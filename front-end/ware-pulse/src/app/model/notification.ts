// src/app/models/notification.ts
export interface Notification {
    id: number;
    message: string;
    timestamp: string;
    read?: boolean;
  }
  