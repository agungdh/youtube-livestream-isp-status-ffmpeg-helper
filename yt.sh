#!/bin/bash
set -euo pipefail

GAMBAR="current.png"
MUSIK="music.mp3"
RTMP_URL="rtmp://a.rtmp.youtube.com/live2/t6m2-g5wt-606q-raqq-fs8v"

# ---------- INPUT ----------
# Gambar: baca ulang file yang sama terus (auto refresh saat file berubah)
IMG_INPUT_OPTS=(
  -f image2
  -pattern_type none   # satu file saja, bukan urutan bernomor
  -loop 1              # loop input; setelah EOF, buka ulang file
  -framerate 30        # masukkan sebagai 30 fps (polling ~30x/detik)
  -i "$GAMBAR"
)

# Audio di-loop tanpa henti
AUD_INPUT_OPTS=(
  -stream_loop -1
  -i "$MUSIK"
)

# ---------- ENCODING ----------
VIDEO_OPTS=(
  -c:v libx264
  -preset veryfast
  -tune stillimage
  -pix_fmt yuv420p
  -r 30          # output CFR 30 fps
  -g 60          # keyframe tiap ~2 dtk
  -bf 0          # tanpa B-frames → DTS monoton
  -vsync cfr
)

AUDIO_OPTS=(
  -c:a aac
  -b:a 128k
  -ar 48000
)

# ---------- MUX ----------
MUX_OPTS=(
  -flvflags no_duration_filesize
  -max_interleave_delta 0
)

ffmpeg \
  "${IMG_INPUT_OPTS[@]}" \
  "${AUD_INPUT_OPTS[@]}" \
  -filter:v "scale=1280:720,format=yuv420p,setsar=1" \
  "${VIDEO_OPTS[@]}" \
  "${AUDIO_OPTS[@]}" \
  "${MUX_OPTS[@]}" \
  -f flv "$RTMP_URL"
