package com.example.openeye.logic.model

data class CommunityBean(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<Item>,
    val nextPageUrl: String,
    val total: Int
) {
    data class Item(
        val adIndex: Int,
        val `data`: Data,
        val id: Int,
        val tag: Any,
        val trackingData: Any,
        val type: String
    ) {
        data class Data(
            val adTrack: Any,
            val content: Content,
            val count: Int,
            val dataType: String,
            val footer: Any,
            val header: Header,
            val itemList: List<Item>
        ) {
            data class Content(
                val adIndex: Int,
                val `data`: Data,
                val id: Int,
                val tag: Any,
                val trackingData: Any,
                val type: String
            ) {
                data class Data(
                    val addWatermark: Boolean,
                    val area: String,
                    val checkStatus: String,
                    val city: String,
                    val collected: Boolean,
                    val consumption: Consumption,
                    val cover: Cover,
                    val createTime: Long,
                    val dataType: String,
                    val description: String,
                    val duration: Int,
                    val height: Int,
                    val id: Int,
                    val ifMock: Boolean,
                    val latitude: Double,
                    val library: String,
                    val longitude: Double,
                    val owner: Owner,
                    val playUrl: String,
                    val playUrlWatermark: String,
                    val privateMessageActionUrl: Any,
                    val reallyCollected: Boolean,
                    val recentOnceReply: Any,
                    val releaseTime: Long,
                    val resourceType: String,
                    val selectedTime: Any,
                    val source: String,
                    val status: String,
                    val tags: List<Tag>,
                    val title: String,
                    val transId: Any,
                    val type: String,
                    val uid: Int,
                    val updateTime: Long,
                    val url: String,
                    val urls: ArrayList<String>,
                    val urlsWithWatermark: List<String>,
                    val validateResult: String,
                    val validateStatus: String,
                    val validateTaskId: String,
                    val width: Int
                ) {
                    data class Consumption(
                        val collectionCount: Int,
                        val realCollectionCount: Int,
                        val replyCount: Int,
                        val shareCount: Int
                    )

                    data class Cover(
                        val blurred: Any,
                        val detail: String,
                        val feed: String,
                        val homepage: Any,
                        val sharing: Any
                    )

                    data class Owner(
                        val actionUrl: String,
                        val area: Any,
                        val avatar: String,
                        val birthday: Long,
                        val city: String,
                        val country: String,
                        val cover: String,
                        val description: String,
                        val expert: Boolean,
                        val followed: Boolean,
                        val gender: String,
                        val ifPgc: Boolean,
                        val job: String,
                        val library: String,
                        val limitVideoOpen: Boolean,
                        val nickname: String,
                        val registDate: Long,
                        val releaseDate: Long,
                        val uid: Int,
                        val university: String,
                        val userType: String
                    )

                    data class Tag(
                        val actionUrl: String,
                        val adTrack: Any,
                        val bgPicture: String,
                        val childTagIdList: Any,
                        val childTagList: Any,
                        val communityIndex: Int,
                        val desc: String,
                        val haveReward: Boolean,
                        val headerImage: String,
                        val id: Int,
                        val ifNewest: Boolean,
                        val name: String,
                        val newestEndTime: Long,
                        val tagRecType: String
                    )
                }
            }

            data class Header(
                val actionUrl: String,
                val cover: Any,
                val followType: String,
                val font: String,
                val icon: String,
                val iconType: String,
                val id: Int,
                val issuerName: String,
                val label: Any,
                val labelList: Any,
                val rightText: Any,
                val showHateVideo: Boolean,
                val subTitle: Any,
                val subTitleFont: Any,
                val tagId: Int,
                val tagName: Any,
                val textAlign: String,
                val time: Long,
                val title: String,
                val topShow: Boolean
            )

            data class Item(
                val adIndex: Int,
                val `data`: Data,
                val id: Int,
                val tag: Any,
                val trackingData: Any,
                val type: String
            ) {
                data class Data(
                    val actionUrl: String,
                    val adTrack: List<Any>,
                    val autoPlay: Boolean,
                    val dataType: String,
                    val description: String,
                    val header: Header,
                    val id: Int,
                    val image: String,
                    val label: Label,
                    val labelList: List<Any>,
                    val shade: Boolean,
                    val title: String
                ) {
                    data class Header(
                        val actionUrl: Any,
                        val cover: Any,
                        val description: Any,
                        val font: Any,
                        val icon: Any,
                        val id: Int,
                        val label: Any,
                        val labelList: Any,
                        val rightText: Any,
                        val subTitle: Any,
                        val subTitleFont: Any,
                        val textAlign: String,
                        val title: Any
                    )

                    data class Label(
                        val card: String,
                        val detail: Any,
                        val text: String
                    )
                }
            }
        }
    }
}