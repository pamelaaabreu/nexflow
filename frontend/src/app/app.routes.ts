import { Routes } from '@angular/router'; import { authGuard } from './core/auth.guard';
export const routes: Routes = [
 {path:'login',loadComponent:()=>import('./features/login/login.component').then(m=>m.LoginComponent)},
 {path:'',canActivate:[authGuard],loadComponent:()=>import('./layout/shell.component').then(m=>m.ShellComponent),children:[
  {path:'dashboard',loadComponent:()=>import('./features/dashboard/dashboard.component').then(m=>m.DashboardComponent)},
  {path:'products',loadComponent:()=>import('./features/products/products.component').then(m=>m.ProductsComponent)},
  {path:'inventory',loadComponent:()=>import('./features/inventory/inventory.component').then(m=>m.InventoryComponent)},
  {path:'orders',loadComponent:()=>import('./features/orders/orders.component').then(m=>m.OrdersComponent)},
  {path:'audit',loadComponent:()=>import('./features/audit/audit.component').then(m=>m.AuditComponent)},
  {path:'',pathMatch:'full',redirectTo:'dashboard'}]},
 {path:'**',redirectTo:''}
];
