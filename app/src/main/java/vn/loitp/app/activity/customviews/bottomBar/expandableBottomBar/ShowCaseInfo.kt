package vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar

data class ShowCaseInfo(
    val title: String, val description: String, val toClass: Class<*>
)

inline fun <reified T> createShowCase(
    title: String, description: String
): ShowCaseInfo {
    return ShowCaseInfo(title = title, description = description, toClass = T::class.java)
}
