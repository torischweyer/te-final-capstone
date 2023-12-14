class RecordingService {
  constructor() {
    this.mediaRecorder = null;
    this.chunks = [];
  }

  startRecording() {
    navigator.mediaDevices.getUserMedia({ audio: true })
      .then((stream) => {
        this.mediaRecorder = new MediaRecorder(stream);
        this.mediaRecorder.addEventListener('dataavailable', (event) => {
          this.chunks.push(event.data);
        });
        this.mediaRecorder.start();
      })
      .catch((error) => {
        console.error('Error accessing microphone:', error);
      });
  }

  stopRecording() {
    return new Promise((resolve, reject) => {
      if (this.mediaRecorder && this.mediaRecorder.state === 'recording') {
        this.mediaRecorder.addEventListener('stop', () => {
          const blob = new Blob(this.chunks, { type: 'audio/webm' });
          resolve(blob);
        });
        this.mediaRecorder.stop();
      } else {
        reject(new Error('No recording in progress.'));
      }
    });
  }
}

export default RecordingService;