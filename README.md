# 🐍 Java Classic Snake Game

A simple yet attractive implementation of the classic arcade game **Snake**, built entirely in Java using the **Swing** and **AWT** libraries for a desktop GUI.

This project is great for beginners looking to understand fundamental Java concepts like:
* **GUI Programming** with `JFrame` and `JPanel`.
* **Game Loop** mechanics using `javax.swing.Timer`.
* **Event Handling** for keyboard input (`KeyAdapter`).
* **2D Graphics** rendering with the `Graphics` class.

---

## 🚀 How to Run the Game

### Prerequisites
* **Java Development Kit (JDK) 8 or higher**
* An IDE or text editor (e.g., VS Code, Eclipse, IntelliJ)

### Running in VS Code

1.  Clone this repository to your local machine.
2.  Open the project folder in VS Code.
3.  Ensure the **"Extension Pack for Java"** is installed.
4.  Open the `SnakeGame.java` file (the main class).
5.  Click the **"Run"** button that appears above the `public static void main(String[] args)` method.

### Game Controls

| Key | Action |
| :--- | :--- |
| **Up Arrow** | Move Up |
| **Down Arrow** | Move Down |
| **Left Arrow** | Move Left |
| **Right Arrow** | Move Right |
| **Spacebar** | Restart Game (After Game Over) |

---

## ⚙️ Core Technologies

* **Language:** Java SE
* **GUI Framework:** Swing (`JFrame`, `JPanel`, `Timer`)
* **Graphics:** AWT (`Graphics`, `Color`, `Font`)

---

## ✨ Features

* **Grid-based Movement:** Clean and responsive cell-by-cell movement.
* **Collision Detection:** Checks for hitting the walls or hitting the snake's own body.
* **Score Tracker:** Updates in real-time.
* **Game Over Screen:** Displays final score and a prompt to restart.