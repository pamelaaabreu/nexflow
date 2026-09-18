export interface UserView { name:string; email:string; role:string; }
export interface AuthResponse { accessToken:string; refreshToken:string; user:UserView; }
export interface Product { id:string; name:string; sku:string; category:string; price:number; stockQuantity:number; reservedQuantity:number; availableQuantity:number; minStock:number; active:boolean; updatedAt:string; }
export type OrderStatus='CREATED'|'PAYMENT_APPROVED'|'SEPARATING'|'READY_TO_SHIP'|'SHIPPED'|'DELIVERED'|'CANCELLED';
export interface OrderItem { productId:string; productName:string; quantity:number; unitPrice:number; total:number; }
export interface Order { id:string; code:string; customerName:string; customerEmail:string; total:number; status:OrderStatus; items:OrderItem[]; createdAt:string; updatedAt:string; }
export interface Dashboard { revenue:number; orders:number; averageTicket:number; criticalStock:number; statusCounts:Record<string,number>; recentOrders:Order[]; topProducts:{name:string;quantity:number;revenue:number}[]; }
export interface Movement { id:string; productId:string; productName:string; sku:string; type:string; quantity:number; reference:string; createdBy:string; createdAt:string; }
export interface AuditEvent { id:string; userEmail:string; action:string; entityName:string; entityId:string; description:string; createdAt:string; }
