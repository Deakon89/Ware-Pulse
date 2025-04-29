import { Client } from './clientMod';

export interface ComplitedOrder {
  id: number;
  productId: number;
  productName: string;
  quantityOrdered: number;
  date: string;      
  client: Client;
}