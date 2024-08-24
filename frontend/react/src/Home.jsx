import {
    Container,
    Stack,
    Flex,
    Box,
    Heading,
    Text,
    Button,
    Image,
    createIcon,
    useColorModeValue, Link, Icon, useDisclosure, Drawer, DrawerContent, CloseButton,
} from '@chakra-ui/react';
//import SidebarWithHeader from "./components/shared/SideBar.jsx";
import React from "react";
import {FiCheck, FiHome, FiPhone, FiSettings, FiUsers} from "react-icons/fi";
import SidebarWithHeader from "./components/shared/SideBar.jsx";

const Home = () => {




    // Use the `useColorModeValue` hook to get a dark background and light text color
    const bgColor = useColorModeValue('gray.900', 'gray.600');
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
                        Welcome! 👋
                    </Text>
                    <br/>
                </Heading>
                <Container maxW={'7xl'}>
                    <Stack
                        align={'center'}
                        spacing={{base: 8, md: 10}}
                        py={{base: 20, md: 28}}
                        direction={{base: 'column', md: 'row'}}>
                        <Stack flex={1} spacing={{base: 5, md: 10}}>
                            <Heading
                                lineHeight={1.1}
                                fontWeight={600}
                                fontSize={{base: '3xl', sm: '4xl', lg: '6xl'}}>
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
                                    Hi,
                                </Text>
                                <br/>
                                <Text as={'span'} color={'red.400'}>
                                    this is my application!
                                </Text>
                            </Heading>
                            <Text color={'black'}>
                                This app lets you login as a customer or create a new account.
                                You can see all users fetched from a database, update, delete or create a new user.<br/>
                                Frontend - React & Angular | Backend - Java & Spring, and many others.<br/>
                                I am so exited to show my new application!
                                Feel free to contact me by clicking 'Contact' button!

                            </Text>
                            <Stack spacing={{base: 4, sm: 6}} direction={{base: 'column', sm: 'row'}}>
                                <Link href="/homepage/customers" _hover={{ textDecoration: 'none' }}>
                                <Button
                                    rounded={'full'}
                                    size={'lg'}
                                    fontWeight={'normal'}
                                    px={6}
                                    colorScheme={'red'}
                                    bg={'red.400'}
                                    _hover={{bg: 'red.500'}}
                                    leftIcon={<PlayIcon h={4} w={4} color={'black'}/>}>
                                    Go to customers
                                </Button>
                                </Link>
                                <Link href="/homepage/howitworks" _hover={{ textDecoration: 'none' }}>
                                <Button
                                    rounded={'full'}
                                    size={'lg'}
                                    fontWeight={'normal'}
                                    px={6}
                                    leftIcon={<QuestionIcon h={4} w={4} color={'black'}/>}>
                                    How it works?
                                </Button>
                                </Link>
                            </Stack>
                        </Stack>
                        <Flex
                            flex={1}
                            justify={'center'}
                            align={'center'}
                            position={'relative'}
                            w={'full'}>
                            <Box
                                position={'relative'}
                                height={'600px'}
                                rounded={'full'}
                                boxShadow="0 30px 60px rgba(0, 0, 0, 0.3), 0 15px 30px rgba(0, 0, 0, 0.2)"
                                width={'full'}
                                overflow={'hidden'}
                            >
                                <Image
                                    alt={'Hero Image'}
                                    fit={'cover'}
                                    align={'center'}
                                    w={'100%'}
                                    h={'100%'}
                                    src={'https://raw.githubusercontent.com/SpartakLysman/Spring-Fullstack-App/main/my_image_2.png'}
                                />
                            </Box>
                        </Flex>
                    </Stack>
                </Container>

            </Box>

        </SidebarWithHeader>
    );
}

const PlayIcon = createIcon({
    displayName: 'PlayIcon',
    viewBox: '0 0 58 58',
    d: 'M28.9999 0.562988C13.3196 0.562988 0.562378 13.3202 0.562378 29.0005C0.562378 44.6808 13.3196 57.438 28.9999 57.438C44.6801 57.438 57.4374 44.6808 57.4374 29.0005C57.4374 13.3202 44.6801 0.562988 28.9999 0.562988ZM39.2223 30.272L23.5749 39.7247C23.3506 39.8591 23.0946 39.9314 22.8332 39.9342C22.5717 39.9369 22.3142 39.8701 22.0871 39.7406C21.86 39.611 21.6715 39.4234 21.5408 39.1969C21.4102 38.9705 21.3421 38.7133 21.3436 38.4519V19.5491C21.3421 19.2877 21.4102 19.0305 21.5408 18.8041C21.6715 18.5776 21.86 18.3899 22.0871 18.2604C22.3142 18.1308 22.5717 18.064 22.8332 18.0668C23.0946 18.0696 23.3506 18.1419 23.5749 18.2763L39.2223 27.729C39.4404 27.8619 39.6207 28.0486 39.7458 28.2713C39.8709 28.494 39.9366 28.7451 39.9366 29.0005C39.9366 29.2559 39.8709 29.507 39.7458 29.7297C39.6207 29.9523 39.4404 30.1391 39.2223 30.272Z',
})


const QuestionIcon = createIcon({
    displayName: 'DotInCircleIcon',
    viewBox: '0 0 24 24',
    path: (
        <>
            {/* Outer Circle */}
            <circle
                cx="12"
                cy="12"
                r="10"
                fill="none"
                stroke="currentColor"
                strokeWidth="3"
            />
            {/* Central Dot */}
            <circle
                cx="12"
                cy="12"
                r="4"
                fill="currentColor"
            />
        </>
    ),
});


export default Home;
