import { playMode } from 'common/js/config'
import { loadSearch, loadPlay, loadFavorite } from 'common/js/cache'

const state = {
  /*  ajax回调状态码 */
  status: 200,
  singer: {},
  playing: false,
  fullScreen: false,
  playlist: [],
  sequenceList: [],
  mode: playMode.sequence,
  currentIndex: -1,
  disc: {},
  topList: {},
  searchHistory: loadSearch(),
  playHistory: loadPlay(),
  favoriteList: loadFavorite(),
  /* 每页查询多少条数据 */
  pageSize: 10,
  
}

export default state
