import { Client } from './clientMod';

export interface CompletedOrder {
  id: number;
  productId: number;
  productName: string;
  quantityOrdered: number;
  date: string;      
  client: Client;
}