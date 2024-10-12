#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <SDL2/SDL_timer.h>

#include <SDL_events.h>
#include <csignal>
#include <cstdint>
#include <iostream>
#include <cstdlib>
#include <ctime>
#include <vector>

void handle_mouse_event(std::vector<std::vector<bool>>& game_state, SDL_MouseButtonEvent b, bool game_start){
    if (b.button == SDL_BUTTON_LEFT && !game_start) {
        int32_t x = b.x / 32;
        int32_t y = b.y / 32;
        if (x >= 0 && x < game_state.size() && y >= 0 && y < game_state[0].size()) {
            game_state[x][y] = !game_state[x][y];
        }
    }
}

void handle_start_event(SDL_Keycode button, bool& game_start){
    if (!game_start && button == SDLK_SPACE){
        std::cout << "space pressed\n";
        game_start = true;
    }

}

int helper(int i, int j, std::vector<std::vector<bool>> state, int WIDTH, int HEIGHT){
    int adj = 0;
    std::cout << "Hello\n";
    for (int x = i-1; x < i+2; x++){
        for (int y = j-1; y < j+2; y++){
            std::cout << "x, y: " << x << ", " << y << std::endl;
            std::cout << "i, j: " << i << ", " << j << std::endl;
            std::cout << std::endl;
            if (x == i && y == j)
                continue;
            else if (0 <= x && x < WIDTH && 0 <= y && y < HEIGHT && state[x][y] == true)
                adj += 1;
        }
    }
    return adj;
}

void iterate_round(std::vector<std::vector<bool>>& game_state, 
                   std::vector<std::vector<bool>> copy_game_state, 
                   bool game_start, int WIDTH, int HEIGHT){
    // RULES
    // Birth: A dead cell becomes alive if it has exactly three live neighbors
    // Death by isolation: A live cell dies if it has one or fewer live neighbors
    // Death by overcrowding: A live cell dies if it has four or more live neighbors
    // Survival: A live cell survives if it has two or three live neighbors

    if(game_start){
       for (int i=0; i<WIDTH; i++){
           for (int j=0; j<HEIGHT; j++){
               bool cell = copy_game_state[i][j];
               int adj = helper(i, j, copy_game_state, WIDTH, HEIGHT);

               if (cell == true){
                   if (adj < 2 || adj > 3)
                       game_state[i][j] = false;
               } else {
                   if (adj == 3)
                       game_state[i][j] = true;
               }
           }
       } 
    }
}

int main(int argc, char *argv[])
{
    SDL_Window* window = nullptr;

    // Error handling
    if (SDL_Init(SDL_INIT_VIDEO) < 0)
        std::cout << "SDL could not be initialized: " << SDL_GetError();
    else
        std::cout << "Program starting...\n";

    // Request window to be created
    window = SDL_CreateWindow("Game of Life", 20, 20, 640, 480, SDL_WINDOW_SHOWN);

    SDL_Renderer* renderer = nullptr;
    renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);

    // Load bitmap of tiles
    SDL_Surface* tile_map_surface = SDL_LoadBMP("../images/tiles.bmp");
    SDL_Surface* clicked_surface  = SDL_LoadBMP("../images/clicked.bmp");
    
    // Create textures from surface
    SDL_Texture* tile_texture    = SDL_CreateTextureFromSurface(renderer, tile_map_surface);
    SDL_Texture* clicked_texture = SDL_CreateTextureFromSurface(renderer, clicked_surface);

    // Loaded into GPU and can free the memory
    SDL_FreeSurface(tile_map_surface);
    SDL_FreeSurface(clicked_surface);

    const int GRID_WIDTH = 20;
    const int GRID_HEIGHT = 15;
    std::vector<std::vector<bool>> game_state(GRID_WIDTH, std::vector<bool>(GRID_HEIGHT, false));

    SDL_Rect tile[GRID_WIDTH][GRID_HEIGHT];
    for (int x = 0; x < GRID_WIDTH; x++) {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            tile[x][y].x = x * 32;
            tile[x][y].y = y * 32;
            tile[x][y].w = 32;
            tile[x][y].h = 32;
        }
    }


    bool game_is_running = true;
    bool game_start = false;
    while(game_is_running){
        SDL_Event e;

        while(SDL_PollEvent(&e)){
            switch(e.type){
                case SDL_QUIT:
                    game_is_running = false;
                    break;
                case SDL_MOUSEBUTTONDOWN:
                    handle_mouse_event(game_state, e.button, game_start);
                    break;
                case SDL_KEYDOWN:
                    std::cout << "Key: " << e.key.keysym.sym << std::endl;
                    handle_start_event(e.key.keysym.sym, game_start);
                    break;
            }
        }

        SDL_SetRenderDrawColor(renderer, 0x66, 0x66, 0xBB, 0xFF);
        SDL_RenderClear(renderer);


        iterate_round(game_state, game_state, game_start, GRID_WIDTH, GRID_HEIGHT);
        for (int x=0; x<GRID_WIDTH; x++){
            for (int y=0; y<GRID_HEIGHT; y++){
                SDL_RenderCopy(renderer, game_state[x][y] ? clicked_texture : tile_texture, NULL, &tile[x][y]);
            }
        }

        // Display renderer to the screen
        SDL_RenderPresent(renderer);

        // Slow down simulation
        SDL_Delay(20);
    }
    
    


    return 0;
}
