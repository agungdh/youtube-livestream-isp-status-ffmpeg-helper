#!/bin/bash
set -euo pipefail

GAMBAR="current.png"
MUSIK="music.mp3"
RTMP_URL="rtmp://a.rtmp.youtube.com/live2/t6m2-g5wt-606q-raqq-fs8v"

# ====== INPUT (hemat polling) ======
# Baca 2 fps saja (cukup untuk gambar statis yang sesekali berubah)
IMG_INPUT_OPTS=(
  -f image2
  -pattern_type none
  -loop 1
  -framerate 1
  -i "$GAMBAR"
)

AUD_INPUT_OPTS=(
  -stream_loop -1
  -i "$MUSIK"
)

# ====== VIDEO ======
VIDEO_OPTS=(
  -c:v libx264
  -preset ultrafast     # paling ringan CPU
  -tune stillimage
  -r 2                  # output 2 fps (CFR)
  -g 4                  # GOP ~2 detik (rekomendasi YouTube ~2s)
  -bf 0                 # tanpa B-frames → aman DTS
  -vsync cfr
  -pix_fmt yuv420p
  -crf 30               # tambah hemat bitrate+CPU (naikkan angka jika mau)
  -threads 2            # batasi core yang dipakai (sesuaikan)
)

# Turunkan resolusi kalau perlu CPU makin hemat (pilih salah satu):
#   720p (default): scale=1280:720
#   480p (lebih hemat): scale=854:480
SCALE_FILTER="scale=1280:720"

# ====== AUDIO ======
AUDIO_OPTS=(
  -c:a aac
  -b:a 96k        # kecilkan bitrate
  -ac 1           # mono (set ke 2 kalau butuh stereo)
  -ar 44100
)

# ====== MUX/RTMP ======
MUX_OPTS=(
  -flvflags no_duration_filesize
  -max_interleave_delta 0
)

ffmpeg \
  "${IMG_INPUT_OPTS[@]}" \
  "${AUD_INPUT_OPTS[@]}" \
  -filter:v "${SCALE_FILTER},format=yuv420p,setsar=1" \
  "${VIDEO_OPTS[@]}" \
  "${AUDIO_OPTS[@]}" \
  "${MUX_OPTS[@]}" \
  -f flv "$RTMP_URL"
