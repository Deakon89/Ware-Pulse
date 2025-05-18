import { Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { AboutComponent } from './component/about/about.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { ProfileComponent } from './component/profile/profile.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { ProductsComponent } from './component/products/products.component';
import { OrdersComponent } from './component/orders/orders.component';
import { ClientsComponent } from './component/clients/clients.component';
import { CompletedOrdersComponent } from './component/completed-orders/completed-orders.component';
import { NotificationsComponent } from './component/notifications/notifications.component';
import { authGuard } from './Auth/auth.guard';

export const routes: Routes = [
    { path: '', component: HomeComponent, children: [
        { path: 'about', component: AboutComponent },
        { path: 'login', component: LoginComponent, data: { animation: 'Login' } },
        { path: 'register', component: RegisterComponent,  },
        { path: 'profile', component: ProfileComponent, canActivate: [authGuard]},
        { path: 'notifications', component: NotificationsComponent},
        { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard], children: [
            { path: '', redirectTo:'products', pathMatch:'full'},
            { path: 'products', component: ProductsComponent},
            { path: 'orders', component: OrdersComponent},
            { path: 'clients', component: ClientsComponent},
            { path: 'complited-orders', component: CompletedOrdersComponent},
        ]},
    ] },
];
