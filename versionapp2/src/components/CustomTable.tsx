import { SafeAreaView, FlatList } from "react-native";
import ItemSeparator from './ItemSeparator';
import ListFooter from './ListFooter';
import ListEmpty from './ListEmpty';

interface CustomTableProps {
    data: any;
    moreLoading: boolean;
    horizontal: boolean;
    numColumns: number;
    displayItem: any;
    listStyle: any;
    viewStyle: any;
    contentContainerStyle: any;
}

interface CustomTableActons {
    reloadHandler: () => void;
    loadMoreData: () => void;
}

const CustomTable = ( props : CustomTableProps, action: CustomTableActons ) => {

    return (
        <SafeAreaView style={props.viewStyle} >
            <FlatList
              data={props.data}
            />
        </SafeAreaView>
    )
};

export default CustomTable;