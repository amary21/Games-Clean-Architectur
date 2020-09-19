##---------------Begin: proguard configuration for DataBinding  ----------
#noinspection ShrinkerUnresolvedReference
-keep class androidx.databinding.** { *; }
-keep class * extends androidx.databinding.DataBinderMapper { *; }
##---------------Begin: proguard configuration for MissingClassFileTransform  ----------
-dontwarn java.lang.instrument.ClassFileTransformer