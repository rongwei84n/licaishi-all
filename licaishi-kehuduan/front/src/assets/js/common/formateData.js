/**
 * 实现phihome中pushDataReceive的timerControl数据的合并
 * @child 收到的新数据
 * @parent 本地存储的原数据
 */
function handlePushDataReceiveData (child, parent) {
  if (!parent) {
    return child;
  }
  let copyJson = JSON.parse(parent);
  let allDataJson = JSON.parse(child);
  return JSON.stringify(extendDeeply(copyJson, allDataJson));
}
/**
 * 实现json对象的深层继承
 * @parent 被继承原对象
 * @child  继承对象(把child对象继承到parent)
 * @result  最终生成的结果对象(参数可省略)
 */
function extendDeeply (parent, child, result) {
  result = result || copyDeeply(parent);
  for (var prop in child) {
    // 如果属性本身,就是一个数组或者Json对象,进行深拷贝
    if (child[prop].constructor === Object) {
      result[prop] = result[prop] || {};
      // 实现深拷贝
      extendDeeply(parent[prop], child[prop], result[prop]);
    } else {
      result[prop] = child[prop];
    }
  }
  return result;
}
/**
 * 实现json对象的深层拷贝
 * @parent 需要拷贝的原对象
 * @child  返回的拷贝完成的对象(参数可省略)
 */
function copyDeeply (parent, child) {
  child = child || {};
  for (var prop in parent) {
    // 如果属性本身,就是一个数组或者Json对象,进行深拷贝
    if (typeof parent[prop] === 'object') {
      // 确定子对象的属性类型,[]或者{}对象
      child[prop] = (parent[prop].constructor === Array) ? [] : {};
      // 实现深拷贝
      copyDeeply(parent[prop], child[prop]);
    } else {
      child[prop] = parent[prop];
    }
  }
  return child;
}
/**
 * 实现创建vux picker的数据
 * @length 每组数据有多少条
 * @times  数据循环多少组
 */
function initData (length, times) {
  times = times || 1;
  let obj = {};
  let result = [];
  for (var t = 0; t < times; t++) {
    for (var i = 0; i < length; i++) {
      if (i < 10) {
        obj = {
          name: '0' + i + '',
          value: '0' + i + '' + t
        };
      } else {
        obj = {
          name: i + '',
          value: i + '' + t
        };
      }
      result.push(obj);
    }
  }
  return result;
}
const HOURSLENGTH = 24;
const HOURSTIMES = 5;
const MINUTESLENGTH = 60;
const MINUTESTIMES = 5;
var timeList = [initData(HOURSLENGTH, HOURSTIMES), initData(MINUTESLENGTH, MINUTESTIMES)]; // 时间初始化

/**
 * 实现创建vux picker的数据
 * @length 每组数据有多少条
 * @times  数据循环多少组
 */
function getDateValue (value) {
  return value.map(function (item, index) {
    return item.substring(0, 2);
  });
}
export {handlePushDataReceiveData, extendDeeply, copyDeeply, initData, HOURSTIMES, MINUTESTIMES, timeList, getDateValue};
