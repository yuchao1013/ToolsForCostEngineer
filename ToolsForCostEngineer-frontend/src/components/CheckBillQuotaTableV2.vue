<template>
  <div>
  <el-upload
      ref="upload"
      class="upload-excel"
      action="http://127.0.0.1:8080/upload/excel"
      :limit="1"
      :on-exceed="handleExceed"
      :auto-upload="true"
      :before-upload="beforeUpload"
      :on-success="handleSuccess"
      :show-file-list="false"
      :accept="allowedType"
  >
    <template #trigger>
      <el-button type="primary">选择excel文件</el-button>
    </template>

    <template #tip>
      <div class="el-upload__tip text-red">
        仅限1个excel文件，新文件会覆盖之前的文件。
      </div>
    </template>
  </el-upload>
  </div>

  <div>
    <el-table-v2
        :columns="columns"
        :data="tableData"
        :width="900"
        :height="400"
        fixed>
<!--      <template #row="props">-->
<!--        <Row v-bind="props" />-->
<!--      </template>-->
    </el-table-v2>

  </div>
</template>

<script setup lang="ts">
import {cloneVNode, ref} from 'vue'
import {genFileId, ElMessage, TableColumnCtx} from 'element-plus'
import { UploadInstance, UploadProps, UploadRawFile} from 'element-plus'

const upload = ref<UploadInstance>()
const handleExceed: UploadProps['onExceed'] = (files) => {
  upload.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value!.handleStart(file)
}

const allowedType = 'application/vnd.ms-excel'
const beforeUpload = (file) => {
  const fileType = file.type;
  const isValidFileType = allowedType.includes(fileType)
  if(!isValidFileType){
    ElMessage.error('只允许上传office2003版的Excel文件，后缀名：.xls');
  }
}


const tableData = ref([])
const handleSuccess = (response) => {
  // 上传成功的回调函数
  tableData.value = response
};


const columns = [
  {
  key: `projectName`,
  dataKey: `projectName`,
  title: `单位工程名称`,
  width: 150,
  },
  {
    key: `billSN`,
    dataKey: `billSN`,
    title: `序号`,
    width: 50,
  },
  {
    key: `billName`,
    dataKey: `billName`,
    title: `清单名称`,
    width: 150,
  },
  {
    key: `billCount`,
    dataKey: `billCount`,
    title: `清单工程量`,
    width: 100,
  },
  {
    key: `quotaName`,
    dataKey: `quotaName`,
    title: `定额名称`,
    width: 150,
  },
  {
    key: `quotaCount`,
    dataKey: `quotaCount`,
    title: `定额工程量`,
    width: 100,
  },
]


</script>

<style scoped>
div{
  width: 70vw;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}
</style>
