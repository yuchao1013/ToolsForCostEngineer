<template>
  <div>
    <el-upload
    action="/api/upload"
    :on-success="handleSuccess"
    :limit="1"
    :file-list="fileList"
    :auto-upload="false"
    :show-file-list="false"
    :before-upload="beforeUpload"
    >
    <el-button type="primary" @click="upload">点击上传文件</el-button>
    </el-upload>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fileList: [],
    };
  },
  methods: {
    handleSuccess(response) {
      // 处理上传成功的回调逻辑
      console.log('上传成功', response);
    },
    upload() {
      this.$refs.upload.submit();
    },
    beforeUpload(file){
      const fileType = file.type;
      const allowedType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      const isValidFileType = allowedType.includes(fileType)
      if(!isValidFileType){
        this.$message.error('只允许上传office2007版以后的Excel文件，后缀名：.xlsx');
      }
    }
  },
};
</script>
