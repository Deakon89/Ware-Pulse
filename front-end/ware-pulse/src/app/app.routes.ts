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
import { ComplitedOrdersComponent } from './component/complited-orders/complited-orders.component';

export const routes: Routes = [
    { path: '', component: HomeComponent, children: [
        { path: 'about', component: AboutComponent },
        { path: 'login', component: LoginComponent },
        { path: 'register', component: RegisterComponent },
        { path: 'profile', component: ProfileComponent},
        { path: 'dashboard', component: DashboardComponent, children: [
            { path: 'products', component: ProductsComponent},
            { path: 'orders', component: OrdersComponent},
            { path: 'clients', component: ClientsComponent},
            { path: 'complited-orders', component: ComplitedOrdersComponent},
        ]},
    ] },
];
