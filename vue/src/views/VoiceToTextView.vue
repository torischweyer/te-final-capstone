<template>
  <div class="container">
  </div>
  <voice-to-text></voice-to-text>
</template>

<script>
import VoiceToText from '../components/VoiceToText.vue'

export default {
  data() {
    return {
      text: ''
    }
  },
  components: {
    VoiceToText
  },
}
</script>

<style scoped>

</style>