import { SafeAreaView, FlatList } from "react-native";
import ItemSeparator from './ItemSeparator';
import ListFooter from './ListFooter';
import ListEmpty from './ListEmpty';

interface CustomListProps {
    data: any;
    moreLoading: boolean;
    horizontal: boolean;
    numColumns: number;
    displayItem: any;
    listStyle: any;
    viewStyle: any;
    contentContainerStyle: any;
}

interface CustomListActons {
    reloadHandler: () => void;
    loadMoreData: () => void;
}

const CustomList = ( props : CustomListProps, action: CustomListActons ) => {

    return (
        <SafeAreaView style={props.viewStyle} >
            <FlatList
              testID={'flatListTestID'}
              data={props.data}
              renderItem={props.displayItem }
              keyExtractor={item => item.id.toString()}
              numColumns={props.numColumns} 
              style={props.listStyle}
              contentContainerStyle={props.contentContainerStyle}
              onEndReachedThreshold={0.2}
              onEndReached={props.loadMoreData}
              ItemSeparatorComponent={<ItemSeparator />}
              ListFooterComponent={<ListFooter loading={action.moreLoading} />}
              ListEmptyComponent={<ListEmpty onPress={ action.reloadHandler }  />}
            />
        </SafeAreaView>
    )
};

export default CustomList;