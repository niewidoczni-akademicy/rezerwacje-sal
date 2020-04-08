FROM node:13 as base
WORKDIR /webapp
COPY package*.json ./
RUN npm install

FROM base as build
COPY public/ public/
COPY src/ src/
RUN npm run build

FROM nginx:alpine as nginx
COPY --from=build /webapp/build /usr/share/nginx/html

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
ENTRYPOINT ["nginx","-g","daemon off;"]
