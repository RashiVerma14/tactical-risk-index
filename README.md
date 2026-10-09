# ⚡ Tactical Risk Index

### AI-Powered Financial Risk Intelligence & Dynamic Index Rebalancing

**S&P Global & Crisil Campus Hackathon 2026**

**Candidate:** Rashi Verma  
**Roll Number:** 23BCE10005  
**University:** VIT Bhopal University  
**Email:** [rashi.23bce10005@vitbhopal.ac.in](mailto:rashi.23bce10005@vitbhopal.ac.in)

---

## 🔗 Project Links

| Resource | Link |
|---|---|
| 💻 Source Code | [GitHub Repository](https://github.com/RashiVerma14/tactical-risk-index) |
| 🎥 Demo Video | [Watch Project Demo](https://youtu.be/-Xl8FVyO3v0) |
| 📊 Presentation | [View Presentation PDF](https://github.com/RashiVerma14/tactical-risk-index/blob/main/docs/presentation.pdf) |
| 🏗️ System Architecture | [View Architecture Diagram](https://github.com/RashiVerma14/tactical-risk-index/blob/main/docs/architecture.png) |

---

## 📌 1. Project Overview

**Tactical Risk Index** is an AI-powered financial risk intelligence system that transforms unstructured financial news into structured risk signals and uses those signals to dynamically rebalance a tactical stock index.

Financial markets continuously generate information through earnings announcements, mergers and acquisitions, regulatory actions, cybersecurity incidents, product launches, and macroeconomic developments. Understanding the potential implications of this information for individual stocks is a key challenge in financial decision-making.

Tactical Risk Index addresses this challenge through an integrated pipeline that collects financial news, analyzes events using a Large Language Model (LLM), identifies affected stocks, evaluates sentiment and impact, and adjusts portfolio allocations accordingly.

The platform presents the resulting risk signals, market information, portfolio metrics, and allocation changes through an interactive dashboard.

### 🎯 Problem Statement

Traditional index allocation approaches may not respond quickly to emerging information contained in financial news. Extracting actionable insights from large volumes of unstructured news also requires time and effort.

The project explores how AI-powered news analysis can support a more responsive, explainable, and data-driven approach to tactical portfolio allocation.

### 💡 Proposed Solution

Tactical Risk Index combines financial news ingestion, LLM-based risk analysis, market data integration, and rule-based portfolio rebalancing in a single application.

It converts news articles into structured information, maps potential risks to relevant stocks, and adjusts their portfolio weights within predefined allocation constraints.

---

## ⚙️ 2. Key Features

- **AI-Powered Financial News Analysis:** Uses an LLM through the Groq API to analyze financial news and extract structured insights.
- **Sentiment Analysis:** Evaluates news sentiment on a scale from -1 to +1.
- **Event Classification:** Identifies the type of financial event described in a news article.
- **Impact Scoring:** Assigns an impact score on a scale from 1 to 10.
- **Affected Stock Identification:** Maps analyzed news events to potentially affected stock tickers.
- **Dynamic Index Rebalancing:** Adjusts portfolio weights using the generated risk signals.
- **Allocation Constraints:** Applies a target allocation range of 5%–15% per stock during rebalancing.
- **Portfolio Normalization:** Normalizes portfolio weights so that the total allocation is 100%.
- **Multi-Source News Ingestion:** Integrates NewsAPI and BBC Business RSS.
- **Market Data Integration:** Uses Alpha Vantage for market data, with fallback values when live data is unavailable.
- **Interactive Dashboard:** Displays portfolio metrics, stock allocations, AI-generated risk signals, and market information.
- **Historical Allocation Visualization:** Uses Chart.js to visualize portfolio weight changes across rebalancing cycles.
- **REST API Integration:** Exposes endpoints for accessing news, risk signals, market data, portfolio information, and historical weights.

---

## 🏗️ 3. System Architecture

The application follows a modular backend architecture that separates API controllers, business logic, data models, and external service integrations.

### Architecture Diagram

![Tactical Risk Index System Architecture](docs/architecture.png)

### End-to-End Workflow

```text
Financial News Sources
        |
        v
News Ingestion
(NewsAPI + BBC Business RSS)
        |
        v
News Processing and Filtering
        |
        v
Groq LLM-Based Risk Analysis
        |
        v
Structured Risk Signals
(Sentiment, Event Type, Impact, Tickers)
        |
        v
Affected Stock Mapping
        |
        v
Tactical Portfolio Rebalancing
        |
        v
Allocation Constraints
(5%–15% per Stock)
        |
        v
Portfolio Normalization
(Total Allocation = 100%)
        |
        v
REST APIs
        |
        v
Interactive Dashboard
(Market Data, Risk Signals,
Portfolio Weights, History)
```

### Main Components

| Component | Responsibility |
|---|---|
| News Ingestion | Collects financial news from configured sources. |
| AI Risk Analysis | Extracts sentiment, event type, impact score, affected tickers, and reasoning. |
| Market Data Integration | Retrieves stock market information from Alpha Vantage and uses fallback values when necessary. |
| Risk Signal Processing | Organizes AI-generated insights into structured risk signals. |
| Rebalancing Engine | Adjusts stock allocations using risk signals and allocation constraints. |
| Portfolio Normalization | Normalizes portfolio weights to maintain a 100% total allocation. |
| REST API Layer | Provides endpoints for dashboard and application functionality. |
| Dashboard | Visualizes market information, portfolio allocations, risk signals, and historical weights. |
| Weight History Service | Maintains recent portfolio allocation snapshots in application memory. |

---

## 🛠️ 4. Technology Stack

| Category | Technologies |
|---|---|
| Programming Language | Java 21 |
| Backend Framework | Spring Boot |
| Build Tool | Maven |
| AI / LLM Integration | Groq API |
| News Sources | NewsAPI, BBC Business RSS |
| Market Data | Alpha Vantage API |
| Frontend | HTML5, CSS3, JavaScript |
| Data Visualization | Chart.js |
| API Architecture | REST APIs |
| Containerization | Docker |
| Version Control | Git, GitHub |
| Development Environment | Visual Studio Code |

### Core Technical Concepts

- Object-Oriented Programming and modular application design
- RESTful API development
- External API integration
- LLM-based structured information extraction
- Financial sentiment and event-impact analysis
- Rule-based portfolio allocation and rebalancing
- Data normalization and constraint handling
- Frontend-to-backend communication
- Historical data visualization

---

## 📈 5. Portfolio and Risk Management

The system works with a 10-stock universe:

| Symbol | Company |
|---|---|
| AAPL | Apple |
| MSFT | Microsoft |
| AMZN | Amazon |
| GOOGL | Alphabet |
| NVDA | NVIDIA |
| META | Meta Platforms |
| JPM | JPMorgan Chase |
| XOM | Exxon Mobil |
| TSLA | Tesla |
| JNJ | Johnson & Johnson |

### Risk Signal Generation

The AI analysis extracts the following information from financial news:

- **Sentiment:** A score between -1 and +1 representing the assessed sentiment.
- **Event Type:** The category of financial event identified in the article.
- **Impact Score:** A score from 1 to 10 representing the assessed impact.
- **Affected Tickers:** Stock symbols potentially affected by the event.
- **Reasoning:** An explanation of the generated analysis.

### Dynamic Rebalancing

The rebalancing process uses the generated risk signals to adjust portfolio weights.

The allocation process applies the following constraints:

- Individual stock allocations are constrained to the configured 5%–15% range.
- Portfolio weights are normalized to total 100%.
- Allocation changes are displayed through the dashboard.
- Historical weight snapshots allow users to observe allocation changes across rebalancing cycles.

The resulting allocations are intended to demonstrate a tactical, news-responsive allocation strategy. They should not be interpreted as guaranteed investment recommendations or predictions of future returns.

---

## 📊 6. Dashboard and Visualization

The dashboard provides a consolidated view of the application's financial risk analysis and portfolio allocation process.

It includes:

- Portfolio allocation and summary metrics
- Stock-level allocation information
- Market prices and daily percentage changes
- AI-generated financial risk signals
- Sentiment and impact information
- Visual allocation bars
- Historical portfolio weight changes

**Historical allocation chart:** Chart.js is used to visualize changes in stock weights over successive recorded snapshots.

**Implementation note:** Historical weight snapshots are currently maintained in application memory and are not persisted across application restarts.

---

## 📰 7. Data Sources

The application integrates external data sources to support its analysis.

| Source | Purpose |
|---|---|
| NewsAPI | Retrieves financial news articles. |
| BBC Business RSS | Provides an additional source of business and financial news. |
| Alpha Vantage | Provides stock market information. |
| Groq API | Processes news through an LLM to generate structured risk analysis. |

When live market data is unavailable, the application can use fallback values.

The output depends on source availability, API limits, news coverage, and the quality of the generated AI analysis.

---

## 🚀 8. Getting Started

### Prerequisites

Install the following before running the application:

- Java Development Kit (JDK) 21
- Git
- An internet connection for external API integrations
- API keys for the external services you want to use

The project includes a Maven Wrapper, so a separate Maven installation is not required for the commands below.

### Step 1: Clone the Repository

```bash
git clone https://github.com/RashiVerma14/tactical-risk-index.git
cd tactical-risk-index
```

### Step 2: Configure API Keys

Configure the following environment variables for the external services:

| Environment Variable | Purpose |
|---|---|
| `GROQ_API_KEY` | Enables LLM-based financial news analysis. |
| `NEWSAPI_KEY` | Enables NewsAPI integration. |
| `ALPHAVANTAGE_API_KEY` | Enables Alpha Vantage market data integration. |

Keep API keys private. Do not commit them to GitHub or place real credentials directly in the source code.

**Windows PowerShell example:**

```powershell
$env:GROQ_API_KEY="your_groq_api_key"
$env:NEWSAPI_KEY="your_newsapi_key"
$env:ALPHAVANTAGE_API_KEY="your_alphavantage_api_key"
```

Replace the example values with your own API keys. These environment variables apply to the current PowerShell session.

### Step 3: Run the Application

On Windows, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

On Linux or macOS, execute:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

### Step 4: Open the Dashboard

Once the application starts successfully, open:

[http://localhost:8080](http://localhost:8080)

The dashboard and available endpoints depend on the application's configuration and the availability of external services.

### Optional: Build the Application

Windows:

```powershell
.\mvnw.cmd clean package
```

Linux or macOS:

```bash
./mvnw clean package
```

---

## 🔌 9. API Endpoints

The application exposes REST endpoints for news retrieval, AI analysis, risk signals, portfolio allocation, and market data.

| Endpoint | Purpose |
|---|---|
| `/api/news` | Retrieves financial news. |
| `/api/groq?text=...` | Sends text for LLM-based analysis. |
| `/api/risk/signals` | Retrieves generated risk signals. |
| `/api/risk/refresh` | Refreshes risk analysis. |
| `/api/index` | Retrieves index information. |
| `/api/index/market` | Retrieves market-related index information. |
| `/api/index/rebalanced` | Retrieves rebalanced index information. |
| `/api/index/dashboard` | Provides dashboard-related information. |
| `/api/index/history` | Retrieves recorded portfolio weight history. |
| `/api/index/history/clear`
