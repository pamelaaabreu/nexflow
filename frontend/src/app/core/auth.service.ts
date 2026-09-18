import { Injectable, computed, signal } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { tap } from "rxjs";
import { AuthResponse, UserView } from "./models";
@Injectable({ providedIn: "root" })
export class AuthService {
  private readonly key = "nexflow.auth";
  private state = signal<AuthResponse | null>(this.load());
  user = computed(() => this.state()?.user ?? null);
  token = computed(() => this.state()?.accessToken ?? null);
  isAuthenticated = computed(() => !!this.state()?.accessToken);
  constructor(private http: HttpClient) {}
  login(email: string, password: string) {
    return this.http
      .post<AuthResponse>("/api/auth/login", { email, password })
      .pipe(tap((r) => this.save(r)));
  }
  refresh() {
    const refreshToken = this.state()?.refreshToken;
    return this.http
      .post<AuthResponse>("/api/auth/refresh", { refreshToken })
      .pipe(tap((r) => this.save(r)));
  }
  logout() {
    localStorage.removeItem(this.key);
    this.state.set(null);
  }
  private save(r: AuthResponse) {
    localStorage.setItem(this.key, JSON.stringify(r));
    this.state.set(r);
  }
  private load() {
    try {
      return JSON.parse(
        localStorage.getItem(this.key) ?? "null",
      ) as AuthResponse | null;
    } catch {
      return null;
    }
  }
}
