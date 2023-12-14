<template>
    <div class="container">
        <button class="button" @click="recordAndSend">Record</button>
    </div>
</template>
  
<script>
import recordingService from '../services/RecordingService.js';
import whisperService from '../services/WhisperService.js';

export default {
    data() {
        return {
            audio: null,
        }
    },
    methods: {
        recordAndSend() {
            recordingService.recordAudio().then((audio) => {
                this.audio = audio;
                whisperService.sendAudio(this.audio);
            });
        }
    }
}
</script>
  
<style scoped>
.container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
}

.button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
}
</style>