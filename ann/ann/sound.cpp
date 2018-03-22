#include "ann.h"

void snares() {
    for (int i = 0; i < (1 << 4); i++) {
        string conc = "";
        for (int j = 0; j < 4; j++) {
            if ((i & (1 << j)) != 0) {
                conc += "sounds/snare.mp3|";
            } else conc += "sounds/white.mp3|";
        }
        string num = to_string(i);
        string command = "ffmpeg -i \"concat:" + conc + "\" -acodec copy sounds/snares/" + num + ".mp3";

        //cout<<command<<"\n";
        system(command.c_str());
    }
}

void kicks() {
    for (int i = 0; i < (1 << 4); i++) {
        string conc = "";
        for (int j = 0; j < 4; j++) {
            if ((i & (1 << j)) != 0) {
                conc += "sounds/kick.mp3|";
            } else conc += "sounds/white.mp3|";
        }
        string num = to_string(i);
        string command = "ffmpeg -i \"concat:" + conc + "\" -acodec copy sounds/kicks/" + num + ".mp3";
        system(command.c_str());
    }
}

void kicksxsnares() {
    int k = 0;
    for (int i = 0; i <16; i++) {
        for (int j = 0; j <16; j++) {
            string i1 = to_string(i);
            string j1 = to_string(j);
            string k1 = to_string(k);
            string command = "ffmpeg -i sounds/kicks/" + i1 + ".mp3 -i sounds/snares/" + j1 + ".mp3 -filter_complex amerge -ac 2 -c:a libmp3lame -q:a 4 sounds/drums/" + k1 + ".mp3";
            system(command.c_str());
            k++;
        }
    }
}

/*static int
on_video_texture_eos(ClutterActor *video) {
    g_object_unref(video);
    return 0;
}

int play_sound(const char *file) {
    
    ClutterActor * video = (ClutterActor *) clutter_gst_video_texture_new();

    clutter_media_set_filename(CLUTTER_MEDIA(video), file);

    clutter_media_set_audio_volume(CLUTTER_MEDIA(video), 1);
    clutter_media_set_playing(CLUTTER_MEDIA(video), TRUE);
    g_signal_connect(video, "eos", G_CALLBACK(on_video_texture_eos), video);
    return 0;
}*/