# Dataset

This folder contains sample synthetic financial news data used for
demonstration and testing of the Tactical Risk Index.

## Source

The sample dataset is synthetic and created specifically for this
hackathon prototype.

The live prototype additionally consumes publicly available data from:

- NewsAPI
- BBC Business RSS
- Alpha Vantage

## Assumptions

- Each news item represents an unstructured financial event.
- The expected stock field identifies the company most directly
  associated with the synthetic headline.
- Sentiment context is provided only for demonstration purposes.
- Production analysis is performed by the Groq-based AI Risk Engine.