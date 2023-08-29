<script setup>
import {computed, reactive, ref} from 'vue';
import {ElForm, ElFormItem, ElInput, ElButton, ElMessage} from 'element-plus';
import {useRoute} from "vue-router";
import {getDataByParam, postByJson} from "@/net";
import {Box, Discount, FullScreen, Money, OfficeBuilding, Tickets} from "@element-plus/icons-vue";
import router from "@/router";
import {useStore} from "@/stores";

const route = useRoute();
const store = useStore();
const manageHouseInfoForm = reactive({
  house_id: '',
  place_name: '',
  unit_number: '',
  building_number: '',
  floor: '',
  door_number: '',
  area: '',
  rent_or_sale: '',
  price: '',
  vacancy_status: '',
  note: ''
});

const rent_or_sale_Options = [
  {value: '出租', label: '出租'},
  {value: '售卖', label: '售卖'}
]

const fetchHouseData = () => {
  getDataByParam('/api/house/get-info', {house_id: route.params.house_id}, (data) => {
    const houseInfo = data.data;

    manageHouseInfoForm.house_id = houseInfo.house_id;
    manageHouseInfoForm.place_name = houseInfo.place_name;
    manageHouseInfoForm.unit_number = houseInfo.unit_number;
    manageHouseInfoForm.building_number = houseInfo.building_number;
    manageHouseInfoForm.floor = houseInfo.floor;
    manageHouseInfoForm.door_number = houseInfo.door_number;
    manageHouseInfoForm.area = houseInfo.area;
    manageHouseInfoForm.rent_or_sale = houseInfo.rent_or_sale;
    manageHouseInfoForm.price = houseInfo.price;
    manageHouseInfoForm.vacancy_status = houseInfo.vacancy_status;
    manageHouseInfoForm.note = houseInfo.note;
  })
}

const formattedHouseInfo = computed(() => {
  return `${manageHouseInfoForm.place_name}:${manageHouseInfoForm.unit_number}单元-${manageHouseInfoForm.building_number}号楼-${manageHouseInfoForm.floor}层-${manageHouseInfoForm.door_number}`;
});

const rules = {
  area: [
    {required: true, message: '请输入面积', trigger: 'blur'},
    {validator: validateArea, trigger: 'blur'}
  ],
  price: [
    {required: true, message: '请输入租售价', trigger: 'blur'},
    {validator: validatePrice, trigger: 'blur'}
  ],
  vacancy_status: [
    {required: true, message: '请选择房屋状态', trigger: 'blur'},
    {validator: validateVacancyStatus, trigger: 'blur'}
  ]
};

function validateArea(rule, value, callback) {
  if (value > 0) {
    callback();
  } else {
    callback(new Error('面积必须大于0'));
  }
}

function validatePrice(rule, value, callback) {
  if (value >= 0) {
    callback();
  } else {
    callback(new Error('租售价必须大于等于0'));
  }
}

function validateVacancyStatus(rule, value, callback) {
  if (value === '未使用' || /^已使用:/.test(value)) {
    callback();
  } else {
    callback(new Error('房屋状态格式不正确'));
  }
}

const manageHouseInfoFormRef = ref()

const back = () => {
  router.push(`/${store.auth.role}/houses`)
};
const submit = () => {
  manageHouseInfoFormRef.value.validate((isValid) => {
    if (isValid) {
      postByJson('/api/house/update-info', manageHouseInfoForm, (message) => {
        ElMessage.success(message);
        back();
      })
    } else {
      ElMessage.warning("请正确填写信息!")
    }
  })
};

fetchHouseData();

</script>

<template>
  <div style="margin-left: 15%">
    <el-form ref="manageHouseInfoFormRef" :model="manageHouseInfoForm"
             :rules="rules" label-width="100px">
      <el-row>
        <el-form-item label="房屋编号" style="width: 18%">
          <el-input :value="manageHouseInfoForm.house_id" readonly type="text">
            <template #prefix>
              <el-icon>
                <Discount/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="位置" style="width: 30%">
          <el-input :value="formattedHouseInfo" readonly type="text">
            <template #prefix>
              <el-icon>
                <OfficeBuilding/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="面积/㎡" prop="area" style="width: 18%">
          <el-input v-model="manageHouseInfoForm.area" :value="manageHouseInfoForm.area" type="number">
            <template #prefix>
              <el-icon>
                <FullScreen/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="使用方式" style="width: 20%">
          <el-select v-model="manageHouseInfoForm.rent_or_sale" :value="manageHouseInfoForm.rent_or_sale"
                     style="width: 100%">
            <el-option
                v-for="item in rent_or_sale_Options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
            <template #prefix>
              <el-icon>
                <Tickets/>
              </el-icon>
            </template>
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="租售价/￥" prop="price" style="width: 20%">
          <el-input v-model="manageHouseInfoForm.price" :value="manageHouseInfoForm.price"
                    type="number">
            <template #prefix>
              <el-icon>
                <Money/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="房屋状态" prop="vacancy_status" style="width: 20%">
          <el-input v-model="manageHouseInfoForm.vacancy_status" :value="manageHouseInfoForm.vacancy_status"
                    type="text">
            <template #prefix>
              <el-icon>
                <Box/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
      </el-row>
      <el-form-item label="备注" style="width: 65%">
        <el-input v-model="manageHouseInfoForm.note" :autosize="{ minRows: 2, maxRows: 4 }"
                  :value="manageHouseInfoForm.note"
                  size="large"
                  type="textarea">
        </el-input>
      </el-form-item>
    </el-form>
  </div>
  <div style="text-align: center;justify-content: center; align-items: center">
    <el-button round size="large" type="default" @click="submit">保存</el-button>
    <el-button round size="large" type="default" @click="back">返回</el-button>
    <div style="font-size: 15px;color: rgb(128,128,128)">房屋状态只能为"未使用"、"审批中"或"已使用:用户ID"</div>
  </div>

</template>

<style scoped>

</style>