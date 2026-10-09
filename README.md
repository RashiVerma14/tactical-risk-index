# ⚡ Tactical Risk Index

### AI-Powered Financial Risk Intelligence & Dynamic Index Rebalancing

> **S&P Global & Crisil Campus Hackathon 2026**

**Rashi Verma**  
**Roll No.: 23BCE10005**  
**VIT Bhopal University**
**rashi.23bce10005@vitbhopal.ac.in**

---

## 🔗 Project Links

| Resource | Link |
|---|---|
| 💻 Source Code | [GitHub Repository](https://github.com/RashiVerma14/tactical-risk-index) |
| 🎥 Demo Video | https://youtu.be/-Xl8FVyO3v0 |
| 📊 Presentation | [`docs/presentation.pdf`](docs/presentation.pdf) |
| 🏗️ Architecture | [`docs/architecture.png`](docs/architecture.png) |
<img width="3497" height="1957" alt="architecture" src="https://github.com/user-attachments/assets/180130cb-1d25-4a88-9fb2-0e732e0d37a4" />


---

# 📌 1. Project Overview

**Tactical Risk Index** is an AI-powered financial risk intelligence system that converts unstructured financial news into structured risk signals and uses those signals to dynamically rebalance a tactical stock index.

Financial markets continuously generate information through earnings announcements, mergers and acquisitions, regulatory actions, cybersecurity incidents, product launches, macroeconomic events and other developments.

The challenge is not simply collecting this information — it is determining:

- What happened?
- Is the information positive or negative?
- How significant is the event?
- Which company or stock is affected?
- Should portfolio exposure change?
- Why did the allocation change?

Tactical Risk Index addresses this problem through an end-to-end pipeline:

```text
Financial News
      ↓
News Ingestion & Filtering
      ↓
AI Risk Analysis
      ↓
Sentiment + Event + Impact
      ↓
Affected Stock Mapping
      ↓
Risk Signals
      ↓
Tactical Rebalancing
      ↓
Weight Constraints
      ↓
Normalization to 100%
      ↓
Interactive Dashboard



## 🛠️ Technology Stack

| Category | Technologies |
|---|---|
| Programming Language | Java 21 |
| Backend Framework | Spring Boot |
| Build Tool | Maven |
| AI & Large Language Model | Groq API, LLM-based financial news analysis |
| News Ingestion | NewsAPI, BBC Business RSS |
| Market Data Integration | Alpha Vantage API |
| Frontend | HTML5, CSS3, JavaScript |
| Data Visualization | Chart.js |
| API Architecture | REST APIs |
| Containerization | Docker |
| Version Control | Git, GitHub |
| Development Environment | Visual Studio Code |

### Key Technical Capabilities

- **AI-Powered Risk Analysis:** Sentiment analysis, financial event classification, impact scoring, and affected-stock identification.
- **Dynamic Index Rebalancing:** Adjusts stock weights using sentiment and impact signals while enforcing 5%–15% allocation constraints.
- **Portfolio Normalization:** Maintains a total portfolio allocation of 100%.
- **Multi-Source News Ingestion:** Collects financial news from NewsAPI and BBC Business RSS.
- **Market Data Integration:** Retrieves stock prices and daily percentage changes through Alpha Vantage, with fallback data.
- **Interactive Dashboard:** Displays AI risk signals, stock allocations, market data, and portfolio weight changes.
- **Historical Weight Visualization:** Uses Chart.js to visualize portfolio allocations across rebalancing cycles.
- **Modular Backend Architecture:** Separates controllers, services, and models for maintainability and extensibility.

### Core Workflow

`Financial News → AI Risk Analysis → Structured Risk Signals → Tactical Rebalancing → Portfolio Normalization → Dashboard Visualization`

