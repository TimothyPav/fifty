#ifndef BUTTON_H
#define BUTTON_H

#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <SDL2/SDL_timer.h>

class Button {
    public:
        SDL_Texture* tex;
        SDL_Rect srect, drect;
        bool isSelected = false;

        Button(SDL_Renderer*);

        void update(SDL_MouseButtonEvent);
        void draw(SDL_Renderer*);
};

#endif
