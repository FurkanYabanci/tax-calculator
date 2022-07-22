# tax-calculator

# Spring Boot Application

Vergi Hesaplama Uygulaması

  
# Özet

Bu proje ürünlerin vergisinin hesaplanarak sisteme kaydedilmesini sağlar.

# Gereksinimler

• Kullanıcı kaydı için gerekli endpointler (firstName, lastName)

• Vergi kaydı için gerekli endpointler (productType, rate)

• Ürün kaydı için gerekli endpointler (name, taxFreePrice, userId, taxId)
___

   Uygulama 3 API içerir.
  
• UserAPI

• TaxAPI

• ProductAPI
___

  GET / api/v1/users - Tüm kullanıcıların listesini getirir.
  
  POST / api/v1/ users – Kullanıcıları sisteme kaydeder.
  
  PUT / api/v1/ users  - Sistemdeki kullanıcıları günceller
  
  DELETE/ api/v1/users/{id} – Sistemden kullanıcı siler.
  ___

  GET /api/v1/taxes/{price}/{productType} – Girilen değere ürün tipine göre vergisini hesaplar.
  
  GET /api/v1/taxes – Tüm vergileri getirir.
  
  POST / api/v1/taxes – Vergileri sisteme kaydeder.
  
  PUT / api/v1/taxes - Sistemdeki vergileri günceller.
  
  DELETE / api/v1/taxes/{id} – Sistemden vergi siler.
  ___
  
  GET / api/v1/products - Tüm ürünlerin listesini getirir.
  
  POST / api/v1/ products – Ürünleri sisteme kaydeder.
  
  PUT / api/v1/ products - Sistemdeki ürünleri günceller.
  
  DELETE / api/v1/ products /{id} – Sistemden ürün siler.

# Teknolojiler

• Java 11

• Spring Boot

• Spring Data JPA

• Restful API 

• PostgreSQL

• Docker


# Ön Koşullar

• Maven

• Docker

# Swagger UI Url

• http://localhost:${port}/swagger-ui.html

  
