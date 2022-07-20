package com.example.openeye.logic.model

data class SearchBean(
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
        val trackingData: TrackingData,
        val type: String
    ) {
        data class Data(
            val actionUrl: String,
            val adTrack: List<Any>,
            val content: Content,
            val `data`: List<Data>,
            val dataType: String,
            val description: String,
            val expert: Boolean,
            val follow: Follow,
            val header: Header,
            val icon: String,
            val iconType: String,
            val id: Int,
            val ifPgc: Boolean,
            val ifShowNotificationIcon: Boolean,
            val subTitle: Any,
            val text: String,
            val title: String,
            val type: String,
            val uid: Int
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
                    val ad: Boolean,
                    val adTrack: List<Any>,
                    val author: Author?,
                    val brandWebsiteInfo: Any,
                    val campaign: Any,
                    val category: String,
                    val collected: Boolean,
                    val consumption: Consumption,
                    val cover: Cover,
                    val dataType: String,
                    val date: Long,
                    val description: String,
                    val descriptionEditor: String,
                    val descriptionPgc: String,
                    val duration: Int,
                    val favoriteAdTrack: Any,
                    val id: Int,
                    val idx: Int,
                    val ifLimitVideo: Boolean,
                    val label: Label,
                    val labelList: List<Label1>,
                    val lastViewTime: Any,
                    val library: String,
                    val playInfo: List<PlayInfo>,
                    val playUrl: String,
                    val played: Boolean,
                    val playlists: Any,
                    val promotion: Promotion,
                    val provider: Provider,
                    val reallyCollected: Boolean,
                    val recallSource: Any,
                    val recall_source: Any,
                    val releaseTime: Long,
                    val remark: String,
                    val resourceType: String,
                    val searchWeight: Int,
                    val shareAdTrack: Any,
                    val slogan: String,
                    val src: Any,
                    val subtitles: List<Any>,
                    val tags: List<Tag>,
                    val thumbPlayUrl: String,
                    val title: String,
                    val titlePgc: String,
                    val type: String,
                    val videoPosterBean: VideoPosterBean,
                    val waterMarks: Any,
                    val webAdTrack: Any,
                    val webUrl: WebUrl
                ) {
                    data class Author(
                        val adTrack: Any,
                        val approvedNotReadyVideoCount: Int,
                        val description: String,
                        val expert: Boolean,
                        val follow: Follow,
                        val icon: String,
                        val id: Int,
                        val ifPgc: Boolean,
                        val latestReleaseTime: Long,
                        val link: String,
                        val name: String,
                        val recSort: Int,
                        val shield: Shield,
                        val videoNum: Int
                    ) {
                        data class Follow(
                            val followed: Boolean,
                            val itemId: Int,
                            val itemType: String
                        )

                        data class Shield(
                            val itemId: Int,
                            val itemType: String,
                            val shielded: Boolean
                        )
                    }

                    data class Consumption(
                        val collectionCount: Int,
                        val realCollectionCount: Int,
                        val replyCount: Int,
                        val shareCount: Int
                    )

                    data class Cover(
                        val blurred: String,
                        val detail: String,
                        val feed: String,
                        val homepage: String,
                        val sharing: Any
                    )

                    data class Label(
                        val card: String,
                        val detail: String,
                        val text: String
                    )

                    data class Label1(
                        val actionUrl: Any,
                        val text: String
                    )

                    data class PlayInfo(
                        val height: Int,
                        val name: String,
                        val type: String,
                        val url: String,
                        val urlList: List<Url>,
                        val width: Int
                    ) {
                        data class Url(
                            val name: String,
                            val size: Int,
                            val url: String
                        )
                    }

                    data class Promotion(
                        val text: String
                    )

                    data class Provider(
                        val alias: String,
                        val icon: String,
                        val name: String
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
                        val newestEndTime: Any,
                        val tagRecType: String
                    )

                    data class VideoPosterBean(
                        val fileSizeStr: String,
                        val scale: Double,
                        val url: String
                    )

                    data class WebUrl(
                        val forWeibo: String,
                        val raw: String
                    )
                }
            }

            data class Data(
                val name: String,
                val trackingData: TrackingData
            ) {
                data class TrackingData(
                    val click: List<Click>,
                    val show: List<Show>
                ) {
                    data class Click(
                        val `data`: Data,
                        val sdk: String
                    ) {
                        data class Data(
                            val element_content: String,
                            val element_name: String,
                            val element_type: String
                        )
                    }

                    data class Show(
                        val `data`: Data,
                        val sdk: String
                    ) {
                        data class Data(
                            val element_content: String,
                            val element_name: String,
                            val element_type: String
                        )
                    }
                }
            }

            data class Follow(
                val followed: Boolean,
                val itemId: Int,
                val itemType: String
            )

            data class Header(
                val actionUrl: String,
                val cover: Any,
                val description: String,
                val font: Any,
                val icon: String,
                val iconType: String,
                val id: Int,
                val label: Any,
                val labelList: Any,
                val rightText: Any,
                val showHateVideo: Boolean,
                val subTitle: Any,
                val subTitleFont: Any,
                val textAlign: String,
                val time: Long,
                val title: String
            )
        }

        data class TrackingData(
            val `data`: Data,
            val dataType: String
        ) {
            data class Data(
                val click: List<Click>,
                val show: List<Show>
            ) {
                data class Click(
                    val `data`: Data,
                    val sdk: String
                ) {
                    data class Data(
                        val card_id: Int,
                        val card_index: Int,
                        val card_title: String,
                        val card_type: String,
                        val click_action: String,
                        val click_action_url: String,
                        val element_id: Int,
                        val element_index: Int,
                        val element_label: String,
                        val element_relative_index: Int,
                        val element_title: String,
                        val element_type: String,
                        val page_type: String
                    )
                }

                data class Show(
                    val `data`: Data,
                    val sdk: String
                ) {
                    data class Data(
                        val card_id: Int,
                        val card_index: Int,
                        val card_title: String,
                        val card_type: String,
                        val element_id: Int,
                        val element_index: Int,
                        val element_label: String,
                        val element_relative_index: Int,
                        val element_title: String,
                        val element_type: String,
                        val page_type: String
                    )
                }
            }
        }
    }
}