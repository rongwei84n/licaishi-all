/**
 * 将时间串转换为固定格式
 *
 * @param {any} time
 * @param {ymd} boolean   获取年月日信息
 */
function formatDateTime({
  time,
  ymd = false,
  ymdHMS = false,
} = {}) {
  const times = new Date(time);
  const year = times.getFullYear(); // 获取年份
  let month = times.getMonth() + 1; // 获取月份
  let date = times.getDate(); // 获取日
  let hours = times.getHours(); // 获取时
  let minutes = times.getMinutes(); // 获取分
  let seconds = times.getSeconds(); // 获取秒

  month = month > 9 ? month : `0${month}`;
  date = date > 9 ? date : `0${date}`;
  hours = hours > 9 ? hours : `0${hours}`;
  minutes = minutes > 9 ? minutes : `0${minutes}`;
  seconds = seconds > 9 ? seconds : `0${seconds}`;

  let resDate = ''
  if (time) {
    resDate = `${year}-${month}`;
  }

  if (time && ymdHMS) {
    resDate = `${year}-${month}-${date}-${hours}-${minutes}-${seconds}`;
  }

  if (time && ymd) {
    resDate = `${year}-${month}-${date}`;
  }
  return resDate
}

export default formatDateTime