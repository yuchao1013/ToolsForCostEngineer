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
    <el-table
        :data="tableData"
        :span-method="objectSpanMethod"
        border
        style="width: 100%"
    >
      <el-table-column prop="projectName" label="单位工程名称" min-width="20%" align="center"/>
      <el-table-column prop="billSN" label="序号"  min-width="5%" align="center"/>
      <el-table-column prop="billName" label="清单名称" min-width="20%" align="center"/>
      <el-table-column prop="billCount" label="清单工程量" min-width="10%" align="center"/>
      <el-table-column prop="quotaName" label="定额名称" min-width="35%" align="center"/>
      <el-table-column prop="quotaCount" label="定额工程量"  min-width="10%" align="center"/>
    </el-table>
  </div>
</template>

<script lang="ts" setup>
import {cloneVNode, ref} from 'vue'
import { genFileId, ElMessage } from 'element-plus'
import { UploadInstance, UploadProps, UploadRawFile, TableColumnCtx } from 'element-plus'

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
interface Result {
  projectName: string
  billSN: string
  billName: string
  billCount: number
  quotaName: string
  quotaCount : number
}

interface SpanMethodProps {
  row: Result
  column: TableColumnCtx<Result>
  rowIndex: number
  columnIndex: number
}

let startIndex0 = 0;
let startIndex1 = 0;
let startIndex2 = 0;
let startIndex3 = 0;
const objectSpanMethod = ({
                            row,
                            column,
                            rowIndex,
                            columnIndex,
                          }: SpanMethodProps,
) => {
  //第1列，单位工程名称
  if (columnIndex === 0) {
    if (rowIndex === startIndex0) {
      let currentName = row.projectName;
      let rowspan = 0;
      // 计算相同值的数量
      for (let i = startIndex0; i < tableData.value.length; i++) {
        if (tableData.value[i].projectName === currentName) {
          rowspan++;
        } else {
          break;
        }
      }
      startIndex0 = rowIndex + rowspan;
      // 返回包含 rowspan 属性的对象
      if (rowspan > 1) {
        return {
          rowspan: rowspan,
          colspan: 1
        }
      }
    } else {
      return {
        rowspan: 0,
        colspan: 0
      }
    }  // 第2列，清单序号
  }else if (columnIndex === 1) {
        if (rowIndex === startIndex1) {
          let currentName = row.billSN;
          let rowspan = 0;
          // 计算相同值的数量
          for (let i = startIndex1; i < tableData.value.length; i++) {
            if (tableData.value[i].billSN === currentName) {
              rowspan++;
            } else {
              break;
            }
          }
          startIndex1 = rowIndex + rowspan;
          // 返回包含 rowspan 属性的对象
          if (rowspan > 1) {
            return {
              rowspan: rowspan,
              colspan: 1
            }
          }
        }else{
          return {
            rowspan: 0,
            colspan: 0
          }
        } // 第3列，清单名称
    }else if (columnIndex === 2) {
    if (rowIndex === startIndex2) {
      let currentName = row.billSN;
      let rowspan = 0;
      // 计算相同值的数量
      for (let i = startIndex2; i < tableData.value.length; i++) {
        if (tableData.value[i].billSN === currentName) {
          rowspan++;
        } else {
          break;
        }
      }
      startIndex2 = rowIndex + rowspan;
      // 返回包含 rowspan 属性的对象
      if (rowspan > 1) {
        return {
          rowspan: rowspan,
          colspan: 1
        }
      }
    }else{
      return {
        rowspan: 0,
        colspan: 0
      }
    }   //第4列，清单工程量
  }else if (columnIndex === 3) {
    if (rowIndex === startIndex3) {
      let currentName = row.billSN;
      let rowspan = 0;
      // 计算相同值的数量
      for (let i = startIndex3; i < tableData.value.length; i++) {
        if (tableData.value[i].billSN === currentName) {
          rowspan++;
        } else {
          break;
        }
      }
      startIndex3 = rowIndex + rowspan;
      // 返回包含 rowspan 属性的对象
      if (rowspan > 1) {
        return {
          rowspan: rowspan,
          colspan: 1
        }
      }
    }else{
      return {
        rowspan: 0,
        colspan: 0
      }
    }
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


</style>