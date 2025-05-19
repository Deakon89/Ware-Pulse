# WarePulse
---

![home](/screenReadme/home.png) 

---
[Ware-Pulse](https://ware-pulse.netlify.app/)

**Warehouse Management Application** per piccoli commercianti.

---

## 🗂 Panoramica del progetto

App full-stack composta da:

* **Back-end**: Spring Boot (Java 17)
* **Front-end**: Angular 19 + Angular Material

L’obiettivo è fornire un sistema di gestione magazzino:

![login](/screenReadme/login.png) 

* **Prodotti**: lista, creazione, modifica, cancellazione
* **Clienti**: creazione, lista
* **Ordini da evadere**: creazione, completamento
* **Ordini evasi**: storico ordini completati
* **Autenticazione**: registrazione, login JWT, profilo utente
* **Notifiche**: stream SSE/WebSocket per aggiornamenti in tempo reale

---

![dashboard](/screenReadme/dash.png) 

---

## 📋 Dipendenze

### Back-end (WarePulse API)

* **Java 17**
* **Spring Boot 3.4.x**
* Spring Security 6
* Spring Data JPA
* jjwt (JSON Web Token)
* MySQL Connector/J
* HikariCP

### Front-end (Dashboard Angular)

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
cd back-end
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

1. **Registrazione**: tramite button sign-in possiamo registrarci a Ware-Pulse.
2. **Login**:  riceviamo un token JWT per entrare nel nostro profilo.
3. **Profilo**: qui abbiamo accesso ai dati e possiamo navigare fino alla dashboard.
4. **Dashboard**: 
   * NavBar per navigare tra Prodotti, Clienti, Ordini, Ordini Evasi
   * Creazione, modifica e cancellazione prodotto
   * Creazione o cancellazione Cliente
   * Creazione o cancellazione ordine (status: `non evaso`)
   * Completamento ordine → spostato in Ordini evasi.
5. **Notifiche**: stream SSE `/notifications/stream`

---

## ⚙️ Configurazioni aggiuntive

* **CORS**: abilitato in `SecurityConfig` per `http://localhost:4200`
* **JWT**: timeout e secret configurabili in `application.properties`
* **Database**: mySQL

---

## 📄 Contributi

Pull request benvenute! Apri issue per bug o feature request.

---

© 2025 WarePulse Team
