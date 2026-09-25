import {
  HttpErrorResponse,
  HttpInterceptorFn,
  HttpRequest,
} from "@angular/common/http";
import { inject } from "@angular/core";
import {
  Observable,
  catchError,
  finalize,
  shareReplay,
  switchMap,
  throwError,
} from "rxjs";

import { AuthService } from "./auth.service";
import { AuthResponse } from "./models";

let refreshRequest$: Observable<AuthResponse> | null = null;

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);

  const isAuthRequest =
    req.url.includes("/api/auth/login") ||
    req.url.includes("/api/auth/refresh");

  const accessToken = auth.token();

  const request =
    accessToken && !isAuthRequest
      ? addAuthorizationHeader(req, accessToken)
      : req;

  return next(request).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status !== 401 || isAuthRequest) {
        return throwError(() => error);
      }

      if (!auth.refreshToken()) {
        auth.logout();

        return throwError(() => error);
      }

      if (!refreshRequest$) {
        refreshRequest$ = auth.refresh().pipe(
          catchError((refreshError) => {
            auth.logout();

            return throwError(() => refreshError);
          }),

          finalize(() => {
            refreshRequest$ = null;
          }),

          shareReplay({
            bufferSize: 1,
            refCount: false,
          }),
        );
      }

      return refreshRequest$.pipe(
        switchMap(() => {
          const newAccessToken = auth.token();

          if (!newAccessToken) {
            auth.logout();

            return throwError(() => error);
          }

          const retryRequest = addAuthorizationHeader(
            req,
            newAccessToken,
          );

          return next(retryRequest);
        }),
      );
    }),
  );
};

function addAuthorizationHeader(
  request: HttpRequest<unknown>,
  token: string,
): HttpRequest<unknown> {
  return request.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });
}