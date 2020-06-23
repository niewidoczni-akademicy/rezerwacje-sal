# Aplikacja do zarządzania salami podczas rekrutacji wstępnych

![code-size](https://img.shields.io/github/languages/code-size/niewidoczni-akademicy/rezerwacje-sal?style=for-the-badge)
![repo-size](https://img.shields.io/github/repo-size/niewidoczni-akademicy/rezerwacje-sal?color=purple&style=for-the-badge)
![languages](https://img.shields.io/github/languages/count/niewidoczni-akademicy/rezerwacje-sal?color=green&style=for-the-badge)
![last-commit](https://img.shields.io/github/last-commit/niewidoczni-akademicy/rezerwacje-sal?color=darkgreen&style=for-the-badge)
![build](https://img.shields.io/circleci/build/github/niewidoczni-akademicy/rezerwacje-sal/develop?&style=for-the-badge)
![issues](https://img.shields.io/github/issues/niewidoczni-akademicy/rezerwacje-sal?&style=for-the-badge)
![pull-requests](https://img.shields.io/github/issues-pr/niewidoczni-akademicy/rezerwacje-sal?style=for-the-badge)
[![circle-ci](https://circleci.com/gh/niewidoczni-akademicy/rezerwacje-sal/tree/develop.svg?style=shield)](https://app.circleci.com/pipelines/github/niewidoczni-akademicy/rezerwacje-sal)


W tym repozytorium znajduje kod aplikacji webowej pozwalającej na rezerwacje sal komputerowych wykorzystywanych przez AGH podczas rekrutacji wstępnych na poszczególne kierunki studiów.

## Spis treści

- [Architektura systemu](#architektura-systemu)
    * [Frontend](#frontend)
    * [Backend](#backend)
    * [Baza danych](#baza-danych)
    * [CI](#ci)
- [Konfiguracja](#konfiguracja)
    * [Wymagania](#wymagania)
    * [Szybki start](#szybki-start)
    * [Zaawansowana konfiguracja](#zaawansowana-konfiguracja)
        * [Plik konfiguracyjny](#plik-konfiguracyjny)
        * [Docker compose](#docker-compose)
        * [Skrypt startowy](#skrypt-startowy)
- [Technologie](#technologie)
- [Dla developerów](#dla-developerów)

## Architektura systemu

Architektura systemu składa się z dwóch podstawowych części. Frontendu odpowiedzialnego za komunikację z klientem oraz backendu odpowiedzialnego za przetwarzanie danych wprowadzonych przez klienta przez przeglądarkę. Dodatkowo w systemie znajdują się jeszcze dwa dodatkowe komponenty, baza danych przechowująca potrzebne do działania aplikacji dane oraz CI sprawdzający poprawność integracji systemu po wprowadzeniu zmian.

### Frontend

Architektura frontendu znajduję się w folderze `rezerwacje-front`, natomiast konfiguracja uruchomieniowa w pliku `docker-compose.yml`. Po więcej szczegółów zobacz [frontend](https://github.com/niewidoczni-akademicy/rezerwacje-sal/tree/develop/rezerwacje-front).

### Backend

Architektura backendu znajduję się w folderze `rezerwacje-app`, natomiast konfiguracja uruchomieniowa w pliku `docker-compose.yml`. Po więcej szczegółów zobacz [backend](https://github.com/niewidoczni-akademicy/rezerwacje-sal/tree/develop/rezerwacje-app).

### Baza danych

Konfiguracja bazy danej znajduję się z pliku `docker-compose.yml` oraz pliku konfiguracyjnym `.env`. Po więcej szczegółów zobacz [zaawansowana konfiguracja](#zaawansowana-konfiguracja).

### CI

Konfiguracja Continuous Integration znajduję się w folderze `.circleci`. Po więcej szczegółów zobacz [CI](https://github.com/niewidoczni-akademicy/rezerwacje-sal/tree/develop/.circleci). 

## Konfiguracja

### Wymagania

 - [Docker + docker compose](https://www.docker.com)
    - [linux](https://get.docker.com)
    - [windows](https://runnable.com/docker/install-docker-on-windows-10) (Wymagane Hyper-V)

### Szybki start

1. Sklonuj repozytorium projektu

```bash
git clone https://github.com/niewidoczni-akademicy/rezerwacje-sal.git
cd rezerwacje-sal
```

2.  Uruchom aplikację

```bash
bash run.sh
```

3.  Otwórz przeglądarkę oraz wpisz następujący url

```
http://localhost:3000
```

4. Zaloguj się za pomocą tymczasowego konta admina

```
Login: admin
Hasło: admin
```
Uwaga: Po dodaniu dowolnego innego użytkownika i restarcie backend'u tymczasowe konto admina znika - nie zapomnij dodać własne konto administracyjne.

### Zaawansowana konfiguracja

Poniższe sekcje dotyczą konfiguracji plików 

#### Plik konfiguracyjny

Konfiguracja aplikacji znajduję się w pliku `.env`.<br>
- Pola obowiązkowe
    - POSTGRES_USER - login użytkownika bazy danych
    - POSTGRES_PASSWORD - hasło użytkownika bazy danych
    - POSTGRES_DB - nazwa bazy danych
- Pola opcjonalne

W przypadku braku pliku konfiguracyjnego zostanie użyta podstawowa konfiguracja znajdująca się w pliku `.example.env`.

#### Docker compose

Konfiguracja startowa aplikacji znajduję w jednym pliku `docker-compose.yml`, plik ten zawiera w sobie konfigurację 3 kontenerów.

- rezerwacje-app
    - kontener backendu
    - komunikuję się zarówno z bazą danych, jak i frontendem
    - api domyślnie działa na porcie 8080
    - konfiguracja pozwala na hot-reloading (patrz #)
- rezerwacje-front
    - kontener frontendu
    - komunikuję się tylko z backendem
    - domyślnie działa na porcie 3000
    - przy zmianie pliku zapewnia hot-reloading
    - zależności (npm install) są tworzone podczas budowania obrazu
- rezerwacje-db
    - kontener bazy danych
    - komunikuję się tylko z backendem
    - dane domyślnie trzymane w volumenie dockera (`docker volume ls`)

#### Skrypt startowy

Przy uruchamianiu aplikacji powinno się korzystać z pliku `run.sh`, w przypadku braku argumentów domyślnie uruchamiana jest cała aplikacja. <br>
W przypadku, gdy chcemy wystartować tylko konkretne kontenery, jako argumenty programu można dodatkowo przekazać ich nazwy:
- rezerwacje-app - kontener backendu
- rezerwacje-front - konetner frontend
- rezerwacje-db - kontener bazy danych

## Technologie

W aplikacji zostały użyte poniższe open-sourcowe narzędzia i technologie

- [Docker](https://www.docker.com)
- [CircleCI](https://circleci.com)
- [React](https://pl.reactjs.org)
- [Nginx](https://www.nginx.com)
- [SpringBoot](https://spring.io/projects/spring-boot)
- [Gradle](https://gradle.org)
- [PostgreSQL](https://www.postgresql.org)

## Dla developerów

- Fronend hot-reloading
    - Automatycznie po zmianie w plikach

- Backend hot-reloading
    - Automatycznie po przebudowaniu projektu gradlowego
    - W celu przebudowania zalecane puszczenie testów `Run Tests in 'rezerwacje...'`, dzięki czemu nie tylko zaaplikuje zmiany, ale też sprawdzimy testy.

- Łączenie się z bazą (Datagrip/InteliJ/pgAdmin):
    - Add Datasource -> PostgreSQL, następnie wypełnij konfigurację

    ```
    user=postgres
    password=postgres
    url=jdbc:postgresql://localhost:5432/rezerwacje
    ```

- Puszczenie testów w dockerze
    - Po wprowadzeniu zmian zaleca się sprawdzenie testów na nowo stworzonym kontenerze przed zrobieniem commita
    - Backend
        - ```docker-compose exec rezerwacje-app ./gradlew test``` - odpalenie testów na chodzącym kontenerze
        - ```docker-compose run rezerwacje-app ./gradlew test``` - odpalenie testów na nowo stworzonym kontenerze
    - Frontend
        - ```docker-compose exec rezerwacje-front npm test``` - odpalenie testów na chodzącym kontenerze
        - ```docker-compose run rezerwacje-front npm test``` - odpalenie testów na nowo stworzonym kontenerze

- Usuwanie danych z bazy danych
    - ```docker-compose down -v``` - usuwa wszystkie kontenery wraz z volumenem danych
