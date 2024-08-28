import {
    Wrap,
    WrapItem,
    Spinner,
    Text,
    Button,
    Box,
    Menu,
    MenuButton,
    MenuList,
    MenuItem,
    Divider
} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import { useEffect, useState } from 'react';
import { getCustomers } from "./services/client.js";
import CardWithImage from "./components/customer/CustomerCard.jsx";
import CreateCustomerDrawer from "./components/customer/CreateCustomerDrawer.jsx";
import { errorNotification } from "./services/notification.js";
import { ChevronDownIcon } from "@chakra-ui/icons";
import SearchFormButton from "./SearchFormButton.jsx";
import { SortCriteria, SortDirection } from "./enums/SortEnums.js"

const Customer = () => {
    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");
    const [customerCount, setCustomerCount] = useState(0);

    const fetchCustomers = (sortBy = SortCriteria.ID, sortDirection = SortDirection.ASC) => {
        setLoading(true);
        getCustomers(sortBy, sortDirection)
            .then(res => {
                setCustomers(res.data);
                setCustomerCount(res.data.length);
            })
            .catch(err => {
                setError(err.response.data.message);
                errorNotification(
                    err.code,
                    err.response.data.message
                );
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const handleSort = (criteria, direction) => {
        fetchCustomers(criteria, direction);
    };

    useEffect(() => {
        fetchCustomers();
    }, []);

    if (loading) {
        return (
            <SidebarWithHeader>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </SidebarWithHeader>
        );
    }

    if (err) {
        return (
            <SidebarWithHeader>
                <CreateCustomerDrawer fetchCustomers={fetchCustomers} />
                <Text mt={5}>Ooops there was an error</Text>
            </SidebarWithHeader>
        );
    }

    if (customers.length <= 0) {
        return (
            <SidebarWithHeader>
                <CreateCustomerDrawer fetchCustomers={fetchCustomers} />
                <Text mt={5}>No customers available</Text>
            </SidebarWithHeader>
        );
    }

    return (
        <SidebarWithHeader>
            <Box position="relative" mb={2}>
                <Text
                    fontSize="lg"
                    align={'center'}
                    position="absolute"
                    top="2"
                    left="50%"
                    transform="translateX(-50%)"
                    fontWeight={'600'}
                    bg="white"
                    px={4}
                    py={1}
                    borderRadius="md"
                    boxShadow="lg"
                >
                    {customerCount} customers
                </Text>
            </Box>

            <CreateCustomerDrawer fetchCustomers={fetchCustomers} />
            <SortButton onSort={handleSort} />
            <SearchFormButton />

            <Wrap justify={"center"} spacing={"30px"}>
                {customers.map((customer, index) => (
                    <WrapItem key={index}>
                        <CardWithImage
                            {...customer}
                            imageNumber={index}
                            fetchCustomers={fetchCustomers}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    );
};

function SortButton({ onSort }) {
    return (
        <Menu>
            <MenuButton
                as={Button}
                rightIcon={<ChevronDownIcon />}
                colorScheme="blue"
                position="relative"
                top="3"
                left="80.3%"
            >
                Sort by
            </MenuButton>
            <MenuList bg="gray.200" color="black">
                <MenuItem onClick={() => onSort(SortCriteria.ID, SortDirection.ASC)}>Id: Lowest</MenuItem>
                <Divider my="0" borderColor="gray.300" />
                <MenuItem onClick={() => onSort(SortCriteria.ID, SortDirection.DESC)}>Id: Highest</MenuItem>
                <Divider my="0" borderColor="gray.300" />
                <MenuItem onClick={() => onSort(SortCriteria.AGE, SortDirection.ASC)}>Age: Lowest</MenuItem>
                <Divider my="0" borderColor="gray.300" />
                <MenuItem onClick={() => onSort(SortCriteria.AGE, SortDirection.DESC)}>Age: Highest</MenuItem>
                <Divider my="0" borderColor="gray.300" />
                <MenuItem onClick={() => onSort(SortCriteria.GENDER, SortDirection.DESC)}>Gender: Male First</MenuItem>
                <Divider my="0" borderColor="gray.300" />
                <MenuItem onClick={() => onSort(SortCriteria.GENDER, SortDirection.ASC)}>Gender: Female First</MenuItem>
                <Divider my="0" borderColor="gray.300" />
                <MenuItem onClick={() => onSort(SortCriteria.IMAGE, SortDirection.ASC)}>Image: With Image</MenuItem>
                <Divider my="0" borderColor="gray.300" />
                <MenuItem onClick={() => onSort(SortCriteria.IMAGE, SortDirection.DESC)}>Image: No Image</MenuItem>
            </MenuList>
        </Menu>
    );
}

export default Customer;
