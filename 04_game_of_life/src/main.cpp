#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <SDL2/SDL_timer.h>
#include <SDL2/SDL_ttf.h>

#include <SDL_events.h>
#include <csignal>
#include <cstdint>
#include <cstdlib>
#include <ctime>
#include <iostream>
#include <vector>

#include "Button.h"

void renderText(SDL_Renderer *renderer, TTF_Font *font, const std::string &text,
                int x, int y, SDL_Color color) {
  SDL_Surface *surface = TTF_RenderText_Blended(font, text.c_str(), color);
  if (!surface) {
    SDL_Log("Failed to render text surface! SDL_ttf Error: %s\n",
            TTF_GetError());
    return;
  }

  SDL_Texture *texture = SDL_CreateTextureFromSurface(renderer, surface);
  if (!texture) {
    SDL_Log("Failed to create texture from rendered text! SDL Error: %s\n",
            SDL_GetError());
    SDL_FreeSurface(surface);
    return;
  }

  SDL_Rect destRect = {x, y, surface->w, surface->h};
  SDL_RenderCopy(renderer, texture, NULL, &destRect);

  SDL_FreeSurface(surface);
  SDL_DestroyTexture(texture);
}

void handle_mouse_event(std::vector<std::vector<bool>> &game_state,
                        SDL_MouseButtonEvent b, bool game_start) {
  if (b.button == SDL_BUTTON_LEFT && !game_start) {
    int32_t x = b.x / 32;
    int32_t y = b.y / 32;
    if (x >= 0 && x < game_state.size() && y >= 0 && y < game_state[0].size()) {
      game_state[x][y] = !game_state[x][y];
    }
  }
}

void handle_start_event(SDL_Keycode button, bool &game_start) {
  if (!game_start && button == SDLK_SPACE) {
    game_start = true;
  } else {
    game_start = false;
  }
}

int helper(int i, int j, std::vector<std::vector<bool>> state, int WIDTH,
           int HEIGHT) {
  int adj = 0;
  for (int x = i - 1; x < i + 2; x++) {
    for (int y = j - 1; y < j + 2; y++) {
      // std::cout << "x, y: " << x << ", " << y << std::endl;
      // std::cout << "i, j: " << i << ", " << j << std::endl;
      // std::cout << std::endl;
      if (x == i && y == j)
        continue;
      else if (0 <= x && x < WIDTH && 0 <= y && y < HEIGHT &&
               state[x][y] == true)
        adj += 1;
    }
  }
  return adj;
}

void iterate_round(std::vector<std::vector<bool>> &game_state,
                   std::vector<std::vector<bool>> copy_game_state,
                   bool game_start, int WIDTH, int HEIGHT, int &counter) {
  // RULES
  // Birth: A dead cell becomes alive if it has exactly three live neighbors
  // Death by isolation: A live cell dies if it has one or fewer live neighbors
  // Death by overcrowding: A live cell dies if it has four or more live
  // neighbors Survival: A live cell survives if it has two or three live
  // neighbors

  if (game_start) {
    for (int i = 0; i < WIDTH; i++) {
      for (int j = 0; j < HEIGHT; j++) {
        bool cell = copy_game_state[i][j];
        int adj = helper(i, j, copy_game_state, WIDTH, HEIGHT);

        if (cell == true) {
          if (adj < 2 || adj > 3)
            game_state[i][j] = false;
        } else {
          if (adj == 3)
            game_state[i][j] = true;
        }
      }
    }
    counter++;
  }
}

int main(int argc, char *argv[]) {
  SDL_Window *window = nullptr;

  // Error handling
  if (SDL_Init(SDL_INIT_VIDEO) < 0)
    std::cout << "SDL could not be initialized: " << SDL_GetError();
  else
    std::cout << "Program starting...\n";

  if (TTF_Init() == -1) {
    std::cerr << "SDL_ttf could not initialize! SDL_ttf Error: "
              << TTF_GetError() << std::endl;
    SDL_Quit();
    return 1;
  }
  // Request window to be created
  window =
      SDL_CreateWindow("Game of Life", 20, 20, 1280, 900, SDL_WINDOW_SHOWN);

  SDL_Renderer *renderer = nullptr;
  renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);

  // Load bitmap of tiles
  SDL_Surface *tile_map_surface = SDL_LoadBMP("../images/tiles.bmp");
  SDL_Surface *clicked_surface = SDL_LoadBMP("../images/clicked.bmp");

  // Create textures from surface
  SDL_Texture *tile_texture =
      SDL_CreateTextureFromSurface(renderer, tile_map_surface);
  SDL_Texture *clicked_texture =
      SDL_CreateTextureFromSurface(renderer, clicked_surface);

  // Loaded into GPU and can free the memory
  SDL_FreeSurface(tile_map_surface);
  SDL_FreeSurface(clicked_surface);

  const int GRID_WIDTH = 32;
  const int GRID_HEIGHT = 23;
  std::vector<std::vector<bool>> game_state(
      GRID_WIDTH, std::vector<bool>(GRID_HEIGHT, false));

  SDL_Rect tile[GRID_WIDTH][GRID_HEIGHT];
  for (int x = 0; x < GRID_WIDTH; x++) {
    for (int y = 0; y < GRID_HEIGHT; y++) {
      tile[x][y].x = x * 32;
      tile[x][y].y = y * 32;
      tile[x][y].w = 32;
      tile[x][y].h = 32;
    }
  }

  // BUTTONS
  Button start_button(renderer);
  Button stop_button(renderer);
  Button reset_button(renderer);
  Button next_button(renderer);
  Button plus_button(renderer);
  Button minus_button(renderer);

  std::vector<Button> button_vector;
  button_vector.push_back(start_button);
  button_vector.push_back(stop_button);
  button_vector.push_back(reset_button);
  button_vector.push_back(next_button);
  button_vector.push_back(plus_button);
  button_vector.push_back(minus_button);

  for (int i = 0; i < button_vector.size(); i++) {
    Button &curr_button = button_vector[i];
    curr_button.srect.y = i * 100;
    curr_button.drect.x = 1027;
    curr_button.drect.y = 15 + (i * 100);
  }

  bool game_is_running = true;
  bool game_start = false;
  int speed = 300;
  int display_speed = 3;
  int counter = 0;

  TTF_Font *font = TTF_OpenFont("../images/Roboto-Black.ttf", 32);
  SDL_Color textColor = {255, 255, 255, 255};

  while (game_is_running) {
    SDL_Event e;

    while (SDL_PollEvent(&e)) {
      switch (e.type) {
      case SDL_QUIT:
        game_is_running = false;
        break;
      case SDL_MOUSEBUTTONDOWN:
        handle_mouse_event(game_state, e.button, game_start);
        for (int i = 0; i < button_vector.size(); i++) {
          Button &curr_button = button_vector[i];
          curr_button.update(e.button);
        }
        if (button_vector[0].isSelected)
          game_start = true;
        if (button_vector[1].isSelected)
          game_start = false;
        if (button_vector[2].isSelected) {
          game_start = false;
          game_state = std::vector<std::vector<bool>>(
              GRID_WIDTH, std::vector<bool>(GRID_HEIGHT, false));
          counter = 0;
        }
        if (button_vector[3].isSelected) {
          if (!game_start) {
            game_start = true;
            iterate_round(game_state, game_state, game_start, GRID_WIDTH,
                          GRID_HEIGHT, counter);
            game_start = false;
          }
        }
        if (button_vector[4].isSelected) {
          if (speed > 0) {
            speed -= 100;
            display_speed += 1;
          }
        }
        if (button_vector[5].isSelected) {
          if (display_speed > 0) {
            speed += 100;
            display_speed -= 1;
          }
        }
        break;
      case SDL_KEYDOWN:
        std::cout << "Key: " << e.key.keysym.sym << std::endl;
        handle_start_event(e.key.keysym.sym, game_start);
        break;
      }
    }

    SDL_SetRenderDrawColor(renderer, 0x66, 0x66, 0xBB, 0xFF);
    SDL_RenderClear(renderer);

    iterate_round(game_state, game_state, game_start, GRID_WIDTH, GRID_HEIGHT,
                  counter);
    for (int x = 0; x < GRID_WIDTH; x++) {
      for (int y = 0; y < GRID_HEIGHT; y++) {
        SDL_RenderCopy(renderer,
                       game_state[x][y] ? clicked_texture : tile_texture, NULL,
                       &tile[x][y]);
      }
    }
    for (int i = 0; i < button_vector.size(); i++) {
      Button &curr_button = button_vector[i];
      // std::cout << "curr: " << curr_button.drect.x << ", " <<
      // curr_button.drect.y << std::endl;
      curr_button.draw(renderer);
    }

    // Text rendering
    std::string iterations = "Iterations: " + std::to_string(counter);
    renderText(renderer, font, iterations, 1024, 670, textColor);

    std::string show_speed = "Current Speed: " + std::to_string(display_speed);
    renderText(renderer, font, show_speed, 1024, 700, textColor);

    // Display renderer to the screen
    SDL_RenderPresent(renderer);

    // Slow down simulation
    if (game_start) {
      SDL_Delay(speed);
    } else {
      SDL_Delay(1);
    }
  }

  return 0;
}
