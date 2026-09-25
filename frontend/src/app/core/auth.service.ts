import { HttpClient } from "@angular/common/http";
import { Injectable, computed, signal } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, tap, throwError } from "rxjs";

import { AuthResponse, UserView } from "./models";

@Injectable({ providedIn: "root" })
export class AuthService {
  private readonly key = "nexflow.auth";

  private readonly state = signal<AuthResponse | null>(this.load());

  readonly user = computed<UserView | null>(
    () => this.state()?.user ?? null,
  );

  readonly token = computed<string | null>(
    () => this.state()?.accessToken ?? null,
  );

  readonly refreshToken = computed<string | null>(
    () => this.state()?.refreshToken ?? null,
  );

  readonly isAuthenticated = computed(
    () => !!this.state()?.accessToken,
  );

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router,
  ) {}

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>("/api/auth/login", {
        email,
        password,
      })
      .pipe(
        tap((response) => this.save(response)),
      );
  }

  refresh(): Observable<AuthResponse> {
    const refreshToken = this.refreshToken();

    if (!refreshToken) {
      return throwError(
        () => new Error("Refresh token não encontrado."),
      );
    }

    return this.http
      .post<AuthResponse>("/api/auth/refresh", {
        refreshToken,
      })
      .pipe(
        tap((response) => this.save(response)),
      );
  }

  logout(): void {
    localStorage.removeItem(this.key);
    this.state.set(null);

    void this.router.navigate(["/login"]);
  }

  private save(response: AuthResponse): void {
    localStorage.setItem(
      this.key,
      JSON.stringify(response),
    );

    this.state.set(response);
  }

  private load(): AuthResponse | null {
    try {
      return JSON.parse(
        localStorage.getItem(this.key) ?? "null",
      ) as AuthResponse | null;
    } catch {
      return null;
    }
  }
}