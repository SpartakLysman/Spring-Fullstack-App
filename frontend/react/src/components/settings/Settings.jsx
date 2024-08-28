import SidebarWithHeader from "../shared/SideBar.jsx";
import {Box, Heading, Text, useColorModeValue} from "@chakra-ui/react";

const Settings = () => {

    const textColor = useColorModeValue('black', 'black');

    return (
        <SidebarWithHeader>
            <Box
                color={textColor}
                minH="100vh"
                transition="3s ease"
                bg={useColorModeValue('mediumturquoise', 'gray.900')}
                border="none"
                w={{ base: 'full', md: 'full' }}
                h="full"
            >
            <Heading
                lineHeight={1.1}
                fontWeight={600}
                fontSize={{base: '3xl', sm: '4xl', lg: '5xl'}}
                textAlign={'center'}
                marginY={39}
            >
                <Text
                    as={'span'}
                    position={'relative'}
                    _after={{
                        content: "''",
                        width: 'full',
                        height: '30%',
                        position: 'absolute',
                        bottom: 1,
                        left: 0,
                        bg: 'red.400',
                        zIndex: -1,
                    }}>
                    Settings 🛠️
                </Text>
                <br/>
            </Heading>
            </Box>
        </SidebarWithHeader>
    )
}

export default Settings;