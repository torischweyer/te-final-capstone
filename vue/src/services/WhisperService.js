const apiKey = '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';
const whisperUrl = 'https://api.openai.com/v1/audio/transcriptions';

export default class WhisperService {
  async sendAudio(audioBlob) {
    const file = audioBlob;
    const model = 'whisper-1';

    const formData = new FormData();
    formData.append('file', file);
    formData.append('model', model);

    const headers = {
      'Authorization': `Bearer ${apiKey}`
    };

    try {
      const response = await fetch(whisperUrl, {
        method: 'POST',
        headers: headers,
        body: formData
      });
      const data = await response.json();
      console.log(data);
      // Handle the response data here
    } catch (error) {
      console.error(error);
      // Handle the error here
    }
  }
}