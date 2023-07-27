<template>
  <div>
  <el-upload
      ref="upload"
      class="upload-excel"
      action="http://127.0.0.1:8080/upload/CheckBillQuotaV2"
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

  <div style="height: 400px">
        <el-table-v2
            :columns="columns"
            :data="tableData"
            :width="900"
            :height="650"
            :row-class="rowClassName"
            :row-height= 30
            fixed>
<!--          <template v-slot:header-cell="row">-->
<!--            <slot v-if="row.rowData.rowType === 0">标</slot>-->
<!--            <slot v-if="row.rowData.rowType === 1">清</slot>-->
<!--            <slot v-if="row.rowData.rowType === 2">定</slot>-->
<!--          </template>-->
        </el-table-v2>
  </div>
</template>

<script setup lang="ts">
import {cloneVNode, ref, h} from 'vue'
import {genFileId, ElMessage, TableColumnCtx, ElTag} from 'element-plus'
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
    key: `sn`,
    dataKey: `sn`,
    title: `序号`,
    width: 50,
    align: "center"
  },
  {
    key: `code`,
    dataKey: `code`,
    title: `项目编码`,
    width: 150,
    align: "center"
  },
  {
    key: `rowType`,
    dataKey: `rowType`,
    title: `类型`,
    width: 50,
    align: "center",
    cellRenderer:function ({cellData}){
      if (cellData === 0){
        return "标"
      }
      if (cellData === 1){
        return "清"
      }
      if (cellData === 2){
        return "定"
      }
    }
  },
  {
    key: `name`,
    dataKey: `name`,
    title: `项目名称`,
    width: 350,
    align: "left"
  },
  {
    key: `unit`,
    dataKey: `unit`,
    title: `单位`,
    width: 50,
    align: "center"
  },
  {
    key: `count`,
    dataKey: `count`,
    title: `工程量`,
    width: 100,
    align: "center"
  }
]

const rowClassName = function (rowProps){
  if (rowProps.rowData.rowType === 0){
    return "title-style"
  }else if (rowProps.rowData.rowType === 1){
    return "bill-style"
  }else if (rowProps.rowData.rowType === 2){
    return "quota-style"
  }
}



</script>

<style scoped>
div{
  width: 70vw;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}

/deep/ .title-style{
  text-align: center;
  color: #ff0000;
}

/deep/ .bill-style{
  text-align: center;
  color: blue;
}

/deep/ .quota-style{
  text-align: center;
  color: black;
}
</style>
