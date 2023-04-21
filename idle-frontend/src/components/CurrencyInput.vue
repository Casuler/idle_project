<template>
  <input ref="inputRef" type="text"/>
</template>

<script setup>
import { useCurrencyInput } from 'vue-currency-input'
import {defineProps} from 'vue'

const props = defineProps({
    modelValue: Number,
    options: Object

})
const inputRef = () => {
    useCurrencyInput(props.options)
}

</script>
<style scoped>

</style>