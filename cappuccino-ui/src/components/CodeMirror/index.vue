<template>
    <div id="code-editor">
        <codemirror v-model="selectValue" :options="editorOptions"/>
    </div>
</template>

<script>
import { codemirror } from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
import "codemirror/theme/material.css"
import 'codemirror/addon/display/autorefresh'
require("codemirror/mode/properties/properties")
require("codemirror/mode/yaml/yaml")

export default {
    name: 'CodeMirror',
    props: {
        value: {
            type: String
        },
        mode: {
            type: String,
            // default: 'properties'
        },
        readOnly: {
            type: Boolean,
            default: false
        }
    },
    components: {
        codemirror
    },
    data() {
        return {
            editorOptions: {
                autoRefresh: true,
                tabSize: 4,
                mode: this.mode,
                theme: 'material',
                lineNumbers: true,
                line: true,
                readOnly: this.readOnly
            }
        }
    },
    computed: {
        selectValue: {
            get: function() {
                return this.value
            },
            set: function(val) {
                // 通知父组件更新绑定值
                this.$emit('input', val)
            }
        },
    },
}
</script>

<style lang="scss">
// #code-editor {
//     // height: 600px;
//     // overflow-y: scroll !important;
//     .CodeMirror {
//         overflow-y: scroll !important;
//         height: auto !important;
//     }
// }

.CodeMirror {
    height: 380px;
    // min-height: 300px;
}
</style>
