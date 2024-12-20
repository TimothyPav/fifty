#include "SudokuBoard.h"
#include <SFML/Graphics.hpp>
#include <SFML/Graphics/Color.hpp>
#include <chrono>
#include <cmath>
#include <functional>
#include <iostream>
#include <string>
#include <unordered_map>

bool contains(const sf::RectangleShape &rec, sf::Vector2i &mousePos) {
  float x{rec.getPosition().x};
  float y{rec.getPosition().y};
  float width{rec.getLocalBounds().width};
  float height{rec.getLocalBounds().height};

  // std::cout << "rec x: " << x << '\n';
  // std::cout << "rec y: " << y << '\n';
  // std::cout << "rec width: " << width << '\n';
  // std::cout << "rec height: " << height << '\n';

  if (x <= mousePos.x && x + width >= mousePos.x) {
    if (y <= mousePos.y && y + height >= mousePos.y) {
      return true;
    }
  }

  return false;
}

int main() {
  sf::RenderWindow window(sf::VideoMode(540, 800), "Sudoku");

  try {
    SudokuBoard sudokuBoard(window);

    sudokuBoard.setValue(0, 3, 2);
    sudokuBoard.setValue(0, 4, 6);
    sudokuBoard.setValue(0, 6, 7);
    sudokuBoard.setValue(0, 8, 1);

    sudokuBoard.setValue(1, 0, 6);
    sudokuBoard.setValue(1, 1, 8);
    sudokuBoard.setValue(1, 4, 7);
    sudokuBoard.setValue(1, 7, 9);

    sudokuBoard.setValue(2, 0, 1);
    sudokuBoard.setValue(2, 1, 9);
    sudokuBoard.setValue(2, 5, 4);
    sudokuBoard.setValue(2, 6, 5);

    sudokuBoard.setValue(3, 0, 8);
    sudokuBoard.setValue(3, 1, 2);
    sudokuBoard.setValue(3, 3, 1);
    sudokuBoard.setValue(3, 7, 4);

    sudokuBoard.setValue(4, 2, 4);
    sudokuBoard.setValue(4, 3, 6);
    sudokuBoard.setValue(4, 5, 2);
    sudokuBoard.setValue(4, 6, 9);

    sudokuBoard.setValue(5, 1, 5);
    sudokuBoard.setValue(5, 5, 3);
    sudokuBoard.setValue(5, 7, 2);
    sudokuBoard.setValue(5, 8, 8);

    sudokuBoard.setValue(6, 2, 9);
    sudokuBoard.setValue(6, 3, 3);
    sudokuBoard.setValue(6, 7, 7);
    sudokuBoard.setValue(6, 8, 4);

    sudokuBoard.setValue(7, 1, 4);
    sudokuBoard.setValue(7, 4, 5);
    sudokuBoard.setValue(7, 7, 3);
    sudokuBoard.setValue(7, 8, 6);

    sudokuBoard.setValue(8, 0, 7);
    sudokuBoard.setValue(8, 2, 3);
    sudokuBoard.setValue(8, 4, 1);
    sudokuBoard.setValue(8, 5, 8);

    sudokuBoard.copyGrid();
    sudokuBoard.draw();
    window.display();
    std::vector<std::pair<sf::RectangleShape, sf::Text>> buttons;

    sf::Text text;
    text.setFont(sudokuBoard.font);
    text.setCharacterSize(40);
    text.setString("Time:");
    text.setPosition(10.f, 550);
    text.setFillColor(sf::Color::Black);
    sudokuBoard.setText(text);

    for (int i = 0; i < 5; i++) {
      int yPos{(i + 1) * 10 + 540 + (73 * i)};
      sf::RectangleShape rec(sf::Vector2f(200.f, 73.f));
      sf::Text text;
      text.setFont(sudokuBoard.font);
      text.setCharacterSize(30);
      rec.setFillColor(sf::Color::Red);
      if (i < 3) {
        rec.move(sf::Vector2f(335.f, yPos));
        text.setPosition(345.f, yPos + 15);
        std::string algo = "Algorithm " + std::to_string(i + 1);
        text.setString(algo);
      } else {
        yPos = {(i + 1 - 2) * 10 + 540 + (73 * (i - 2))};
        rec.move(sf::Vector2f(10.f, yPos));
        text.setPosition(20.f, yPos + 15);
        std::string option;
        if (i == 3)
          option = "Shuffle";
        else if (i == 4)
          option = "Reset";
        text.setString(option);
      }
      buttons.push_back({rec, text});
    }

    sudokuBoard.setButtons(buttons);

    std::unordered_map<std::string, std::function<bool()>> funcMap;
    funcMap["Algorithm 1"] = [&sudokuBoard, &text]() {
      // time start
      auto start = std::chrono::high_resolution_clock::now();

      bool solved = sudokuBoard.solveBacktrack(0, 0);
      // time end
      auto end = std::chrono::high_resolution_clock::now();
      std::chrono::duration<double> elapsed = end - start;

      std::string timeElapsed = "Time: " + std::to_string(elapsed.count());
      text.setString(timeElapsed);

      return solved;
    };
    funcMap["Algorithm 2"] = [&sudokuBoard, &text]() {
      auto start = std::chrono::high_resolution_clock::now();
      bool solved = sudokuBoard.betterSolveBacktrack();
      auto end = std::chrono::high_resolution_clock::now();
      std::chrono::duration<double> elapsed = end - start;
      std::string timeElapsed = "Time: " + std::to_string(elapsed.count());
      text.setString(timeElapsed);

      return solved;
    };
    funcMap["Algorithm 3"] = [&sudokuBoard, &text]() {
      auto start = std::chrono::high_resolution_clock::now();
      bool solved = sudokuBoard.betterSolveBacktrack();
      auto end = std::chrono::high_resolution_clock::now();
      std::chrono::duration<double> elapsed = end - start;
      std::string timeElapsed = "Time: " + std::to_string(elapsed.count());
      text.setString(timeElapsed);

      return solved;
    };
    funcMap["Shuffle"] = [&sudokuBoard]() {
      sudokuBoard.shuffle();
      return true;
    };
    funcMap["Reset"] = [&sudokuBoard]() {
      sudokuBoard.reset();
      return true;
    };

    static bool lock_click;
    while (window.isOpen()) {
      window.clear(sf::Color::White);
      sf::Event event;

      while (window.pollEvent(event)) {
        if (event.type == sf::Event::Closed)
          window.close();
        if (event.type == sf::Event::MouseButtonPressed &&
            event.mouseButton.button == sf::Mouse::Left && lock_click != true) {
          lock_click = true;
          // std::cout << "Left Click: " << sf::Mouse::getPosition(window).x <<
          // ", " << sf::Mouse::getPosition(window).y << '\n';
          for (const auto &pair : buttons) {
            sf::Vector2i mousePos = sf::Mouse::getPosition(window);
            if (contains(pair.first, mousePos)) {
              std::string buttonText = pair.second.getString().toAnsiString();
            }
          }
        }
        if (event.type == sf::Event::MouseButtonReleased &&
            event.mouseButton.button == sf::Mouse::Left)
          lock_click = false;
      }
      sudokuBoard.draw();
      for (int i = 0; i < buttons.size(); i++) {
        window.draw(buttons[i].first);
        window.draw(buttons[i].second);
      }
      window.draw(text);
      window.display();
    }

  } catch (const std::exception &e) {
    return 1;
  }

  return 0;
}
