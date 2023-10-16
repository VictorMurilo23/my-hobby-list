FROM node:16-alpine AS build
WORKDIR /app-frontend
ARG CONFIGURATION
COPY . .
RUN npm install
RUN npm run build -- --configuration=$CONFIGURATION
# Serve Application using Nginx Server
FROM nginx:alpine
COPY ./nginx.conf /etc/nginx/conf.d/default.conf 
COPY --from=build /app-frontend/dist/my-hobby-list-frontend/ /usr/share/nginx/html
