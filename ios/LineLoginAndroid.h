
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNLineLoginAndroidSpec.h"

@interface LineLoginAndroid : NSObject <NativeLineLoginAndroidSpec>
#else
#import <React/RCTBridgeModule.h>

@interface LineLoginAndroid : NSObject <RCTBridgeModule>
#endif

@end
