# Sleuth Waiter Service ⚡

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2024.0.2-blue.svg)](https://spring.io/projects/spring-cloud)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 服務介紹

Sleuth Waiter Service 是一個整合了分散式鏈路追蹤功能的咖啡店服務員服務，負責處理咖啡訂單的製作流程。本服務展示了如何在Spring Boot應用中實現完整的鏈路追蹤功能，包括與Zipkin的整合、跨服務調用追蹤，以及訊息佇列事件的鏈路傳遞。

> 💡 **為什麼選擇此服務？**
> - 完整的微服務架構實作範例
> - 整合分散式鏈路追蹤技術
> - 支援訊息驅動的事件處理
> - 提供完整的監控與可觀測性

### 🎯 服務特色

- **分散式鏈路追蹤** - 整合Micrometer Tracing與Zipkin
- **訊息佇列整合** - 支援RabbitMQ事件驅動架構
- **服務註冊發現** - 整合Consul服務發現機制
- **資料持久化** - 使用JPA進行訂單資料管理
- **容錯處理** - 整合Resilience4j熔斷器

## 技術棧

### 核心框架
- **Spring Boot 3.4.5** - 應用程式基礎框架
- **Spring Cloud 2024.0.2** - 微服務架構支援
- **Spring Data JPA** - 資料持久化層

### 微服務組件
- **Spring Cloud Consul Discovery** - 服務註冊與發現
- **Spring Cloud Consul Config** - 配置中心整合
- **Spring Cloud Stream Rabbit** - 訊息驅動架構
- **Micrometer Tracing** - 分散式鏈路追蹤
- **Resilience4j** - 熔斷器與容錯處理

### 資料存儲與中間件
- **MariaDB** - 訂單資料持久化
- **RabbitMQ** - 訊息佇列事件處理
- **Consul** - 服務註冊與配置管理
- **Zipkin** - 鏈路追蹤資料收集

### 開發工具與輔助
- **Lombok** - 程式碼生成工具
- **Joda Money** - 貨幣金額處理
- **Jackson** - JSON序列化框架
- **Apache Commons Lang3** - 常用工具庫

## 服務架構

```
sleuth-waiter-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── tw/fengqing/spring/springbucks/waiter/
│   │   │       ├── config/           # 配置類
│   │   │       ├── controller/       # REST控制器
│   │   │       ├── model/           # 資料模型
│   │   │       ├── repository/      # 資料存取層
│   │   │       ├── service/         # 業務邏輯層
│   │   │       ├── support/         # 輔助工具類
│   │   │       └── WaiterServiceApplication.java
│   │   └── resources/
│   │       ├── application.properties    # 應用配置
│   │       └── bootstrap.properties      # 啟動配置
│   └── test/
├── pom.xml                          # Maven依賴管理
└── README.md                        # 服務說明文件
```

## 快速開始

### 前置需求
- **Java 21** 或更高版本
- **Maven 3.6+** 建置工具
- **Docker** 容器運行環境
- **MariaDB** 資料庫
- **RabbitMQ** 訊息佇列
- **Consul** 服務註冊中心
- **Zipkin** 鏈路追蹤服務

### 環境準備

1. **啟動依賴服務：**
```bash
# 啟動MariaDB資料庫
docker run -d --name mariadb -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=spring \
  -e MYSQL_DATABASE=springbucks \
  -e MYSQL_USER=spring \
  -e MYSQL_PASSWORD=spring \
  mariadb:latest

# 啟動RabbitMQ訊息佇列
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=spring \
  -e RABBITMQ_DEFAULT_PASS=spring \
  rabbitmq:3-management

# 啟動Consul服務註冊中心
docker run -d --name consul -p 8500:8500 consul:latest

# 啟動Zipkin鏈路追蹤
docker run -d --name zipkin -p 9411:9411 openzipkin/zipkin:latest
```

### 安裝與執行

1. **編譯專案：**
```bash
cd "Chapter 16 服務鏈路追蹤/sleuth-waiter-service"
mvn clean compile
```

2. **執行服務：**
```bash
mvn spring-boot:run
```

3. **驗證服務啟動：**
```bash
# 檢查服務健康狀態
curl http://localhost:8080/actuator/health

# 查看服務註冊狀態
curl http://localhost:8500/v1/agent/services
```

## 進階說明

### 環境變數配置
```properties
# 資料庫連線設定
DB_URL=jdbc:mariadb://localhost:3306/springbucks
DB_USERNAME=spring
DB_PASSWORD=spring

# Consul服務發現設定
CONSUL_HOST=localhost
CONSUL_PORT=8500

# RabbitMQ訊息佇列設定
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=spring
RABBITMQ_PASSWORD=spring

# Zipkin鏈路追蹤設定
ZIPKIN_ENDPOINT=http://localhost:9411/api/v2/spans
TRACING_SAMPLING_PROBABILITY=1.0
```

### 重要配置說明
```properties
# application.properties 主要設定
spring.application.name=sleuth-waiter-service
server.port=8080

# 鏈路追蹤配置
management.tracing.enabled=true
management.zipkin.tracing.endpoint=${ZIPKIN_ENDPOINT}
management.tracing.sampling.probability=${TRACING_SAMPLING_PROBABILITY}

# 服務發現配置
spring.cloud.consul.host=${CONSUL_HOST}
spring.cloud.consul.port=${CONSUL_PORT}

# 訊息佇列配置
spring.rabbitmq.host=${RABBITMQ_HOST}
spring.rabbitmq.port=${RABBITMQ_PORT}
spring.rabbitmq.username=${RABBITMQ_USERNAME}
spring.rabbitmq.password=${RABBITMQ_PASSWORD}
```

### API端點說明

| 端點 | 方法 | 說明 | 範例 |
|------|------|------|------|
| `/coffee` | GET | 取得咖啡菜單 | `GET /coffee` |
| `/coffee/{id}` | GET | 取得特定咖啡資訊 | `GET /coffee/1` |
| `/orders` | GET | 取得所有訂單 | `GET /orders` |
| `/orders/{id}` | GET | 取得特定訂單 | `GET /orders/1` |
| `/orders/{id}` | PUT | 更新訂單狀態 | `PUT /orders/1` |
| `/actuator/health` | GET | 服務健康檢查 | `GET /actuator/health` |
| `/actuator/metrics` | GET | 服務指標監控 | `GET /actuator/metrics` |

## 鏈路追蹤功能

### 追蹤特性
- **自動追蹤** - 所有HTTP請求和資料庫操作自動追蹤
- **跨服務追蹤** - 與Customer Service的調用鏈路追蹤
- **訊息追蹤** - RabbitMQ訊息事件的鏈路傳遞
- **自定義標籤** - 支援業務相關的追蹤標籤

### 追蹤日誌格式
```
INFO [sleuth-waiter-service,1234567890abcdef,1234567890abcdef] - 訂單處理開始
DEBUG [sleuth-waiter-service,1234567890abcdef,1234567890abcdef] - 呼叫資料庫查詢
INFO [sleuth-waiter-service,1234567890abcdef,1234567890abcdef] - 訂單狀態更新完成
```

### Zipkin查詢範例
```bash
# 查詢特定Trace ID
curl "http://localhost:9411/api/v2/traces/1234567890abcdef"

# 查詢服務調用統計
curl "http://localhost:9411/api/v2/dependencies"
```

## 監控與可觀測性

### 健康檢查端點
- **健康狀態** - `/actuator/health`
- **應用資訊** - `/actuator/info`
- **指標監控** - `/actuator/metrics`
- **追蹤資訊** - `/actuator/tracing`

### 重要指標
- **JVM記憶體使用** - `jvm.memory.used`
- **HTTP請求計數** - `http.server.requests`
- **資料庫連線池** - `hikaricp.connections`
- **RabbitMQ訊息處理** - `rabbitmq.consumer.count`

## 注意事項與最佳實踐

### ⚠️ 重要提醒

| 項目 | 說明 | 建議做法 |
|------|------|----------|
| 鏈路追蹤 | 採樣比例設定 | 生產環境建議0.1，開發環境可設1.0 |
| 服務註冊 | 健康檢查配置 | 確保Consul能正確檢測服務狀態 |
| 訊息處理 | 事務一致性 | 使用@Transactional確保資料一致性 |
| 錯誤處理 | 異常監控 | 整合異常追蹤與告警機制 |

### 🔒 最佳實踐指南

- **程式碼註解** - 在重要的業務邏輯處添加清楚的中文註解，特別是鏈路追蹤相關的程式碼
- **配置外部化** - 將資料庫連線、服務發現等配置外部化
- **監控告警** - 設定關鍵指標的監控告警閾值
- **日誌規範** - 統一日誌格式，包含Trace ID和Span ID
- **測試覆蓋** - 撰寫單元測試驗證業務邏輯正確性

## 故障排查

### 常見問題

1. **服務無法註冊到Consul**
   ```bash
   # 檢查Consul服務狀態
   curl http://localhost:8500/v1/agent/services
   
   # 檢查服務配置
   cat src/main/resources/application.properties | grep consul
   ```

2. **鏈路追蹤資料不顯示**
   ```bash
   # 檢查Zipkin服務狀態
   curl http://localhost:9411/api/v2/services
   
   # 驗證採樣配置
   grep "sampling.probability" src/main/resources/application.properties
   ```

3. **RabbitMQ連線失敗**
   ```bash
   # 檢查RabbitMQ管理介面
   # 訪問 http://localhost:15672
   # 帳號: spring, 密碼: spring
   ```

## 相關服務

- **Customer Service** - 客戶端服務，負責訂單建立
- **Barista Service** - 咖啡師服務，負責咖啡製作
- **Config Server** - 配置中心服務
- **Zipkin Server** - 鏈路追蹤服務

## 授權說明

本專案採用 MIT 授權條款，詳見 LICENSE 檔案。

## 關於我們

我們主要專注在敏捷專案管理、物聯網（IoT）應用開發和領域驅動設計（DDD）。喜歡把先進技術和實務經驗結合，打造好用又靈活的軟體解決方案。

## 聯繫我們

- **FB 粉絲頁**：[風清雲談 | Facebook](https://www.facebook.com/profile.php?id=61576838896062)
- **LinkedIn**：[linkedin.com/in/chu-kuo-lung](https://www.linkedin.com/in/chu-kuo-lung)
- **YouTube 頻道**：[雲談風清 - YouTube](https://www.youtube.com/channel/UCXDqLTdCMiCJ1j8xGRfwEig)
- **風清雲談 部落格**：[風清雲談](https://blog.fengqing.tw/)
- **電子郵件**：[fengqing.tw@gmail.com](mailto:fengqing.tw@gmail.com)

---

**📅 最後更新：2024年12月**  
**👨‍💻 維護者：風清雲談團隊**
