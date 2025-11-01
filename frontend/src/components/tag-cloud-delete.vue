<template>
    <div class="cloud"
         :style="{ width: width+'px', height:height+'px' }"
         @touchmove="listener1" >
		 <!-- @mousemove="listener2" -->
	<div
	    class="cloud-item"
	    v-for="cloud of clouds"
	    :style="{
		transform: 
`translate3d(${cloud.x}px,${cloud.y}px,${cloud.z}px) scale(${(400+cloud.z)/600})`,
		opacity: (400 + cloud.z) / 600,
		color: cloud.color
	}">
	    {{cloud.text}}
	</div>
    </div>
</template>

<script lang="ts" setup>
import { computed, onMounted, onUnmounted, reactive } from 'vue'

interface Cloud {
	x: number
	y: number
	z: number
	text: string
	color?: string
}

interface Tag {
	 name: string
	 color?: string
}

const prop = withDefaults(defineProps<{
	tags: Tag[]
	width?: string  // 宽
	height?: string // 高
	radius?: string // 半径
}>(), { width: '600', height: '600', radius: '250' })

let v = Math.PI / 360 //旋转速度 
let speedX = 0   //以x轴为基准旋转
let speedY = v   //以y轴为基准旋转
const clouds:Cloud[] = reactive([])

// 球心坐标
const CX = computed(() => { return parseInt(prop.width) / 3.5 })
const CY = computed(() => { return parseInt(prop.height) / 2 })

// 创建标签
const createTags = (): Cloud[] => {
	let clouds: Cloud[] = []
	for (let i = 0, len = prop.tags.length; i < len; i++) {
		let k = -1 + (2 * (i + 1) - 1) / len
		let a = Math.acos(k)
		let b = a * Math.sqrt(len * Math.PI)	
		let cloud: Cloud = {
			x: CX.value + +prop.radius * Math.sin(a) * Math.cos(b),
			y: CY.value + +prop.radius * Math.sin(a) * Math.sin(b),
			z: +prop.radius * Math.cos(a),
			text: prop.tags[i].name,
			color: prop.tags[i].color || '#333'
		}
		clouds.push(cloud)
	}
	return clouds
}

clouds.push(...createTags())

// 云球旋转函数-X
const rotateX = (angleX: number) => {
	let cos = Math.cos(angleX)
	let sin = Math.sin(angleX)
	for (let cloud of clouds) {
		let _y = (cloud.y - CY.value) * cos - cloud.z * sin + CY.value
		let _z = cloud.z * cos + (cloud.y- CY.value) * sin
		cloud.y = _y
		cloud.z = _z
        // console.log(cloud.y)
	}
}
// 云球旋转函数-Y
const rotateY = (angleY: number) => {
	let cos = Math.cos(angleY)
	let sin = Math.sin(angleY)
	for (let cloud of clouds) {
		let _x = (cloud.x - CX.value) * cos - cloud.z * sin + CX.value
		let _z = cloud.z * cos + (cloud.x - CX.value) * sin
		cloud.x = _x
		cloud.z = _z
        // console.log(cloud.x)
	}
}

// 定时器
let timer: number
onMounted(() => {
	timer = setInterval(() => {
		rotateX(speedX)
		rotateY(speedY)
	}, 10)
})
onUnmounted(() => {
	clearInterval(timer)
})

// 手指滑动事件
const listener1 = (event: TouchEvent) => {
	let x = event.touches[0].pageX - CX.value
	let y = event.touches[0].pageX - CY.value
	speedX = x * 0.0001 > 0 ? Math.min(+prop.radius * 0.00002, x * 0.0001) :
                                  Math.max(-prop.radius * 0.00002, x * 0.0001)
	speedY = y * 0.0001 > 0 ? Math.min(+prop.radius * 0.00002, y * 0.0001) :
                                  Math.max(-prop.radius * 0.00002, y * 0.0001)
}
// // 鼠标滑动事件
// const listener2 = (event: MouseEvent) => {
// 	let x = event.clientX - CX.value
// 	let y = event.clientY - CY.value
// 	speedX = x * 0.0001 > 0 ? Math.min(+prop.radius * 0.00002, x * 0.0001) :
//                                   Math.max(-prop.radius * 0.00002, x * 0.0001)
// 	speedY = y * 0.0001 > 0 ? Math.min(+prop.radius * 0.00002, y * 0.0001) :
//                                   Math.max(-prop.radius * 0.00002, y * 0.0001)
// }
</script>

<style lang="scss" scoped>
.cloud {
	position: relative;
	.cloud-item {
		position: absolute;
		padding: 10px 20px;
		border-radius: 10px;
		font-size: 30px;
		transition: all 2s linear;
	}
}
</style>