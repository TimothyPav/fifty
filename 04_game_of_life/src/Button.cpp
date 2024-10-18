#include "Button.h"

Button::Button(SDL_Renderer* r) {
    static SDL_Texture* t = IMG_LoadTexture(r, "../images/button_sheet.png");
    tex = t;

    srect.h = 100;
    srect.w = 100;
    srect.x = 0;

    drect.h = 100;
    drect.w = 100;
}

void Button::update(SDL_MouseButtonEvent b){
    if (b.button == SDL_BUTTON_LEFT) {
        SDL_Point click_point = {b.x, b.y};
        if (SDL_PointInRect(&click_point, &drect)){
            isSelected = true;
        } else {
            isSelected = false;
        }
    }
}

void Button::draw(SDL_Renderer* r) {
    SDL_RenderCopy(r, tex, &srect, &drect);
}
