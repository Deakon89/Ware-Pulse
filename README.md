# WarePulse

[Ware-Pulse](https://ware-pulse.netlify.app/)

**Warehouse Management Application** per piccoli commercianti.

---

## 🗂 Panoramica del progetto

App full-stack composta da:

* **Back-end**: Spring Boot (Java 21)
* **Front-end**: Angular 19 + Angular Material

L’obiettivo è fornire un sistema di gestione magazzino:

* **Prodotti**: lista, creazione, modifica, cancellazione
* **Clienti**: lista, CRUD
* **Ordini da evadere**: invio, completamento
* **Ordini evasi**: storico ordini completati
* **Autenticazione**: registrazione, login JWT, profilo utente
* **Notifiche**: stream SSE/WebSocket per aggiornamenti in tempo reale

---

## 📋 Dipendenze

### Back-end (WarePulse API)

* **Java 21**
* **Spring Boot 3.4.x**
* Spring Security 6
* Spring Data JPA
* jjwt (JSON Web Token)
* MySQL Connector/J
* HikariCP

### Front-end (Dashboard Angular)

* **Node.js** >= 18
* **Angular CLI** 19.x
* @angular/core, @angular/router, @angular/common
* @angular/material
* rxjs

---

## 🔧 Installazione

### 1. Clonazione del repository

```bash
# cartella principale
git clone https://github.com/tuo-username/Ware-Pulse.git
cd Ware-Pulse
```

### 2. Back-end

```bash
cd back-end/ware-pulse
# configurare src/main/resources/application.properties:
# spring.datasource.url=jdbc:mysql://localhost:3306/warepulse
# spring.datasource.username=utente_db
# spring.datasource.password=password_db
# jwt.secret=TUO_SECRET

# build e run con Maven
tmvn clean package
java -jar target/ware-pulse-0.0.1-SNAPSHOT.jar
```

### 3. Front-end

```bash
cd front-end/ware-pulse
npm install
ng serve --open
```

L’app Angular verrà avviata su `http://localhost:4200` e comunicherà con l’API su `http://localhost:8080`.

---

## 🚀 Utilizzo

1. **Registrazione**: POST `/api/auth/register`
2. **Login**: POST `/api/auth/login` → ricevuta token JWT
3. **Profilo**: GET `/api/auth/me` (header `Authorization: Bearer <jwt>`)
4. **Dashboard** Angular:

   * Sidenav laterale per navigare tra Prodotti, Clienti, Ordini, Ordini Evasi
   * Tabelle dinamiche con servizi Angular (HttpClient)
5. **CRUD** Prodotti & Clienti: chiamate a `/api/products`, `/api/clients`
6. **Gestione Ordini**:

   * Creazione ordine (status: `PENDING`)
   * Completamento ordine → spostato in `/api/completed-orders`
7. **Notifiche**: stream SSE `/api/notifications/stream`

---

## ⚙️ Configurazioni aggiuntive

* **CORS**: abilitato in `SecurityConfig` per `http://localhost:4200`
* **JWT**: timeout e secret configurabili in `application.properties`
* **Database**: aggiornare URL, credenziali

---

## 📄 Contributi

Pull request benvenute! Apri issue per bug o feature request.

---

© 2025 WarePulse Team
