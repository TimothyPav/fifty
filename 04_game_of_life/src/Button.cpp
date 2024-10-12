#include "Button.h"
#include <iostream>

Button::Button(SDL_Renderer* r) {
    static SDL_Texture* t = IMG_LoadTexture(r, "../images/button_sheet.png");
    tex = t;

    srect.h = 100;
    srect.w = 200;
    srect.x = 0;

    drect.h = 75;
    drect.w = 250;
}

void Button::update(SDL_MouseButtonEvent b){
    if (b.button == SDL_BUTTON_LEFT) {
        SDL_Point click_point = {b.x, b.y};
        if (SDL_PointInRect(&click_point, &drect)){
            isSelected = true;
            std::cout << "Button Clicked\n";
        } else {
            isSelected = false;
        }
    }
}

void Button::draw(SDL_Renderer* r) {
    SDL_RenderCopy(r, tex, &srect, &drect);
}
