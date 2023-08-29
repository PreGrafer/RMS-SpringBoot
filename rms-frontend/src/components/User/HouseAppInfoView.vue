<script setup>
import {computed, ref, reactive} from "vue";
import {useStore} from "@/stores";
import {useRoute} from "vue-router";
import {ElButton, ElForm, ElFormItem, ElInput, ElMessage} from "element-plus";
import {Box, Discount, FullScreen, Money, OfficeBuilding, Tickets} from "@element-plus/icons-vue";
import {getDataByParam, postByJson} from "@/net";
import router from "@/router";

const route = useRoute();
const store = useStore();

const houseInfo = reactive({
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
const formattedHouseInfo = computed(() => {
  return `${houseInfo.place_name}:${houseInfo.unit_number}单元-${houseInfo.building_number}号楼-${houseInfo.floor}层-${houseInfo.door_number}`;
});

const houseApplicationFormRef = ref()
const houseApplicationForm = reactive({
  user_id: store.auth.userid,
  house_id: route.params.house,
  start_date: '',
  end_date: '',
  note: ''
});

const back = () => {
  router.push(`/user/house-app`)
};
const submit = () => {
  houseApplicationFormRef.value.validate((isValid) => {
    if (isValid) {
      if ((houseInfo.rent_or_sale === '出租' && houseApplicationForm.start_date && houseApplicationForm.end_date) || (houseInfo.rent_or_sale === '售卖'))
        postByJson('/api/use-house/post-application', houseApplicationForm, (message) => {
          ElMessage.success(message);
          back();
        })
      else {
        ElMessage.warning("请正确填写信息!")
      }
    } else {
      ElMessage.warning("请正确填写信息!")
    }
  })
};

const fetchHouseInfo = () => {
  getDataByParam('/api/house/get-info', {house_id: route.params.house}, (data) => {
    const HouseData = data.data;

    houseInfo.house_id = HouseData.house_id;
    houseInfo.place_name = HouseData.place_name;
    houseInfo.unit_number = HouseData.unit_number;
    houseInfo.building_number = HouseData.building_number;
    houseInfo.floor = HouseData.floor;
    houseInfo.door_number = HouseData.door_number;
    houseInfo.area = HouseData.area;
    houseInfo.rent_or_sale = HouseData.rent_or_sale;
    houseInfo.price = HouseData.price;
    houseInfo.vacancy_status = HouseData.vacancy_status;
    houseInfo.note = HouseData.note;
  })
}

const rules = {
  start_date: [
    {validator: validateStartDate, trigger: 'change', when: houseInfo.rent_or_sale === '出租'}
  ],
  end_date: [
    {validator: validateEndDate, trigger: 'change', when: houseInfo.rent_or_sale === '出租'}
  ]
};

function validateStartDate(rule, value, callback) {
  if (value && value <= new Date()) {
    console.log(value)
    console.log(new Date())
    callback(new Error('开始必须在今天之后'));
  } else {
    callback();
  }
}

function validateEndDate(rule, value, callback) {
  if (value && value <= houseApplicationForm.start_date) {
    callback(new Error('结束日期必须在开始日期之后'));
  } else {
    callback();
  }
}

fetchHouseInfo();
</script>

<template>
  <div style="margin-left: 15%">
    <el-form :model="houseInfo" label-width="100px">
      <el-row>
        <el-form-item label="房屋编号" style="width: 18%">
          <el-input :value="houseInfo.house_id" readonly type="text">
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
        <el-form-item label="面积/㎡" style="width: 18%">
          <el-input v-model="houseInfo.area" :value="houseInfo.area" readonly type="number">
            <template #prefix>
              <el-icon>
                <FullScreen/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="使用方式" style="width: 20%">
          <el-input :value="houseInfo.rent_or_sale" readonly type="text">
            <template #prefix>
              <el-icon>
                <Tickets/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="租售价/￥" style="width: 20%">
          <el-input :value="houseInfo.price" readonly type="number">
            <template #prefix>
              <el-icon>
                <Money/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="房屋状态" style="width: 20%">
          <el-input :value="houseInfo.vacancy_status" readonly type="text">
            <template #prefix>
              <el-icon>
                <Box/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
      </el-row>
      <el-form-item label="备注" style="width: 65%">
        <el-input :value="houseInfo.note" readonly size="large" type="textarea"></el-input>
      </el-form-item>
    </el-form>
  </div>
  <div style="margin-left: 15%">
    <el-form ref="houseApplicationFormRef" :model="houseApplicationForm" :rules="rules" label-width="100px">
      <el-row>
        <el-form-item label="开始时间" prop="start_date" style="width: 30%">
          <el-date-picker
              v-model="houseApplicationForm.start_date"
              placeholder="选择开始日期"
              size="large"
              type="date"
          />
        </el-form-item>
        <el-form-item label="结束时间" prop="end_date" style="width: 30%">
          <el-date-picker
              v-model="houseApplicationForm.end_date"
              placeholder="选择结束日期"
              size="large"
              type="date"
          />
        </el-form-item>
      </el-row>
      <el-form-item label="备注" style="width: 65%">
        <el-input v-model="houseApplicationForm.note" size="large" type="textarea"></el-input>
      </el-form-item>
    </el-form>
  </div>
  <div style="text-align: center;justify-content: center; align-items: center">
    <el-button round size="large" type="default" @click="submit">申请</el-button>
    <el-button round size="large" type="default" @click="back">返回</el-button>
    <div style="font-size: 15px;color: rgb(128,128,128)">选择"出租"房时 必须填写时间! 不足一月按一月计算</div>
  </div>
</template>

<style scoped>

</style>