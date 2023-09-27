# Getting Started

## Building Event-Driven Architectures with Kalix

Kalix is a platform as a service (PaaS) designed to streamline the development and operations of event-driven microservices and APIs. Harness the full potential of event-driven systems while minimizing operational costs.

## Getting Started with Kalix Event-Driven Development

1. **Install the Kalix CLI**

    - **Windows:** [Download](https://downloads.kalix.io/latest/kalix_windows_amd64.zip), verify, extract, and add `kalix.exe` to your `%PATH%`.
    - **macOS:** [Installation Guide](https://docs.kalix.io/docs/cli/installation/mac/)
    - **Linux:** [Installation Guide](https://docs.kalix.io/docs/cli/installation/linux/)

2. **Register for a Kalix Account**

    ```
    kalix auth signup
    ```

3. **Kickstart Your Event-Driven Journey**

    Explore Kalix's Quickstarts tailored for event-driven development:

    - [Java Event-Driven Quickstart](https://docs.kalix.io/docs/quickstarts/java-event-driven/)
    - [Scala Event-Driven Quickstart](https://docs.kalix.io/docs/quickstarts/scala-event-driven/)

=== "Java"

    ```java title="src/CounterEntity.java"
    --8<-- "src/CounterEntity.java"
    ```

    1. Every Entity must be annotated with `@TypeId` with a stable identifier. This identifier should be unique among the different existing entities within a Kalix application.

    2. The `@Id` value should be unique per entity and map to some field being received on the route path, in this example it’s the counter_id.
    
    3. The `CounterEntity` class should extend `kalix.javasdk.valueentity.ValueEntity`.

=== "Scala"

    ```scala title="src/CounterEntity.scala"
    --8<-- "src/CounterEntity.scala"
    ```

    1. The validation ensures acceptance of positive values and it fails calls with illegal values by returning an `Effect`` with `effects.error`.

    2. From the current state we create a new state with the increased value.
    
    3. We store the new state by returning an `Effect` with `effects.updateState`.
